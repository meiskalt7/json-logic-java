package io.github.meiskalt7.jsonlogic.ast;

import io.github.meiskalt7.jsonlogic.JsonLogicException;

class JsonLogicParseException extends JsonLogicException {

    public JsonLogicParseException(String msg) {
        super(msg);
    }

    public JsonLogicParseException(Throwable cause) {
        super(cause);
    }
}
