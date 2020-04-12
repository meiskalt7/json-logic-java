package io.github.meiskalt7.jsonlogic;

public class JsonLogicException extends Exception {

    private JsonLogicException() {
        // The default constructor should not be called for exceptions. A reason must be provided.
    }

    protected JsonLogicException(String msg) {
        super(msg);
    }

    protected JsonLogicException(Throwable cause) {
        super(cause);
    }

    protected JsonLogicException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
