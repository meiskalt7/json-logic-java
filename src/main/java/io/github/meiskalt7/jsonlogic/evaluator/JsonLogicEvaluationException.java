package io.github.meiskalt7.jsonlogic.evaluator;

import io.github.meiskalt7.jsonlogic.JsonLogicException;

public class JsonLogicEvaluationException extends JsonLogicException {

    public JsonLogicEvaluationException(String msg) {
        super(msg);
    }

    public JsonLogicEvaluationException(Throwable cause) {
        super(cause);
    }
}
