package io.github.meiskalt7.jsonlogic.evaluator.expressions;

import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.meiskalt7.jsonlogic.utils.ArrayLike;
import io.github.meiskalt7.jsonlogic.utils.MapLike;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MissingExpression implements PreEvaluatedArgumentsExpression {

    public static final MissingExpression ALL = new MissingExpression(false);
    public static final MissingExpression SOME = new MissingExpression(true);

    private final boolean isSome;

    private MissingExpression(boolean isSome) {
        this.isSome = isSome;
    }

    /**
     * Given a map structure such as: {a: {b: 1}, c: 2}
     *
     * This method will return the following set: ["a.b", "c"]
     */
    private static Set getFlatKeys(Map map) {
        return getFlatKeys(map, "");
    }

    private static Set getFlatKeys(Map map, String prefix) {
        Set keys = new LinkedHashSet();

        for (Object pair : map.entrySet()) {
            Map.Entry entry = (Map.Entry) pair;

            if (MapLike.isEligible(entry.getValue())) {
                keys.addAll(
                    getFlatKeys(new MapLike(entry.getValue()), prefix + entry.getKey() + "."));
            } else {
                keys.add(prefix + entry.getKey());
            }
        }

        return keys;
    }

    @Override
    public String key() {
        return isSome ? "missing_some" : "missing";
    }

    @Override
    public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
        if (!MapLike.isEligible(data)) {
            return arguments;
        }

        if (isSome && (!ArrayLike.isEligible(arguments.get(1)) || !(arguments
            .get(0) instanceof Double))) {
            throw new JsonLogicEvaluationException(
                "missing_some expects first argument to be an integer and the second " +
                    "argument to be an array");
        }

        Map map = new MapLike(data);
        List options = isSome ? new ArrayLike(arguments.get(1)) : arguments;
        Set providedKeys = getFlatKeys(map);
        Set requiredKeys = new LinkedHashSet(options);

        requiredKeys.removeAll(providedKeys); // Keys that I need but do not have

        if (isSome && options.size() - requiredKeys.size() >= ((Double) arguments.get(0))
            .intValue()) {
            return Collections.EMPTY_LIST;
        }

        return new ArrayList<>(requiredKeys);
    }
}
