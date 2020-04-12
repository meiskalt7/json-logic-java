package io.github.meiskalt7.jsonlogic.evaluator;

import io.github.meiskalt7.jsonlogic.ast.JsonLogicArray;

public interface JsonLogicExpression {

    String key();

    Object evaluate(JsonLogicEvaluator evaluator, JsonLogicArray arguments, Object data)
        throws JsonLogicEvaluationException;
}
