package io.github.meiskalt7.jsonlogic.evaluator.expressions;

import io.github.meiskalt7.jsonlogic.ast.JsonLogicArray;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluationException;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluator;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicExpression;
import io.github.meiskalt7.jsonlogic.utils.ArrayLike;
import java.util.List;

public interface PreEvaluatedArgumentsExpression extends JsonLogicExpression {

    Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException;

    @Override
    default Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
        throws JsonLogicEvaluationException {
        List<Object> values = evaluator.evaluate(arguments, data);

        if (values.size() == 1 && ArrayLike.isEligible(values.get(0))) {
            values = new ArrayLike(values.get(0));
        }

        return evaluate(values, data);
    }
}
