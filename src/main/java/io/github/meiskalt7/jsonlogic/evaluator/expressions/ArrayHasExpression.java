package io.github.meiskalt7.jsonlogic.evaluator.expressions;

import io.github.meiskalt7.jsonlogic.JsonLogic;
import io.github.meiskalt7.jsonlogic.ast.JsonLogicArray;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluator;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicExpression;
import io.github.meiskalt7.jsonlogic.utils.ArrayLike;

public class ArrayHasExpression implements JsonLogicExpression {

    public static final ArrayHasExpression SOME = new ArrayHasExpression(true);
    public static final ArrayHasExpression NONE = new ArrayHasExpression(false);

    private final boolean isSome;

    private ArrayHasExpression(boolean isSome) {
        this.isSome = isSome;
    }

    @Override
    public String key() {
        return isSome ? "some" : "none";
    }

    @Override
    public Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
        throws JsonLogicEvaluationException {
        if (arguments.size() != 2) {
            throw new JsonLogicEvaluationException("some expects exactly 2 arguments");
        }

        Object maybeArray = evaluator.evaluate(arguments.get(0), data);

        if (!ArrayLike.isEligible(maybeArray)) {
            throw new JsonLogicEvaluationException("first argument to some must be a valid array");
        }

        for (Object item : new ArrayLike(maybeArray)) {
            if (JsonLogic.truthy(evaluator.evaluate(arguments.get(1), item))) {
                return isSome;
            }
        }

        return !isSome;
    }
}
