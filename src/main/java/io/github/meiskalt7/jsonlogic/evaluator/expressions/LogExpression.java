package io.github.meiskalt7.jsonlogic.evaluator.expressions;

import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluationException;
import java.io.PrintStream;
import java.util.List;

public class LogExpression implements PreEvaluatedArgumentsExpression {

    public static final LogExpression STDOUT = new LogExpression(System.out);

    private final PrintStream printer;

    private LogExpression(PrintStream printer) {
        this.printer = printer;
    }

    @Override
    public String key() {
        return "log";
    }

    @Override
    public Object evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
        if (arguments.isEmpty()) {
            throw new JsonLogicEvaluationException("log operator requires exactly 1 argument");
        }

        Object value = arguments.get(0);
        printer.println("JsonLogic: " + value);

        return value;
    }
}
