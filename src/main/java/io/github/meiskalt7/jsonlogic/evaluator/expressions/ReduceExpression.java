package io.github.meiskalt7.jsonlogic.evaluator.expressions;

import io.github.meiskalt7.jsonlogic.ast.JsonLogicArray;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluator;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicExpression;
import io.github.meiskalt7.jsonlogic.utils.ArrayLike;
import java.util.HashMap;
import java.util.Map;

public class ReduceExpression implements JsonLogicExpression {

    public static final ReduceExpression INSTANCE = new ReduceExpression();

    private ReduceExpression() {
        // Use INSTANCE instead.
    }

    @Override
    public String key() {
        return "reduce";
    }

    @Override
    public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
        throws JsonLogicEvaluationException {
        if (arguments.size() != 3) {
            throw new JsonLogicEvaluationException("reduce expects exactly 3 arguments");
        }

        Object maybeArray = evaluator.evaluate(arguments.get(0), data);
        Object accumulator = evaluator.evaluate(arguments.get(2), data);

        if (!ArrayLike.isEligible(maybeArray)) {
            return accumulator;
        }

        Map<String, Object> context = new HashMap<>();
        context.put("accumulator", accumulator);

        for (Object item : new ArrayLike(maybeArray)) {
            context.put("current", item);
            context.put("accumulator", evaluator.evaluate(arguments.get(1), context));
        }

        return context.get("accumulator");
    }
}
