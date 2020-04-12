package io.github.meiskalt7.jsonlogic;

import com.google.gson.Gson;
import io.github.meiskalt7.jsonlogic.ast.JsonLogicNode;
import io.github.meiskalt7.jsonlogic.ast.JsonLogicParser;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicEvaluator;
import io.github.meiskalt7.jsonlogic.evaluator.JsonLogicExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.AllExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.ArrayHasExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.ConcatenateExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.EqualityExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.FilterExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.IfExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.InExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.InequalityExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.LogExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.LogicExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.MapExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.MathExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.MergeExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.MissingExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.NotExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.NumericComparisonExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.PreEvaluatedArgumentsExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.ReduceExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.StrictEqualityExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.StrictInequalityExpression;
import io.github.meiskalt7.jsonlogic.evaluator.expressions.SubstringExpression;
import io.github.meiskalt7.jsonlogic.utils.Utils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Java implementation of http://jsonlogic.com/
 */
public final class JsonLogic {

    private final List<JsonLogicExpression> expressions;
    private final Map<String, JsonLogicNode> parseCache;
    private JsonLogicEvaluator evaluator;

    public JsonLogic() {
        expressions = new ArrayList<>();
        parseCache = new ConcurrentHashMap<>();

        // Add default operations
        addOperation(MathExpression.ADD);
        addOperation(MathExpression.SUBTRACT);
        addOperation(MathExpression.MULTIPLY);
        addOperation(MathExpression.DIVIDE);
        addOperation(MathExpression.MODULO);
        addOperation(MathExpression.MIN);
        addOperation(MathExpression.MAX);
        addOperation(NumericComparisonExpression.GT);
        addOperation(NumericComparisonExpression.GTE);
        addOperation(NumericComparisonExpression.LT);
        addOperation(NumericComparisonExpression.LTE);
        addOperation(IfExpression.IF);
        addOperation(IfExpression.TERNARY);
        addOperation(EqualityExpression.INSTANCE);
        addOperation(InequalityExpression.INSTANCE);
        addOperation(StrictEqualityExpression.INSTANCE);
        addOperation(StrictInequalityExpression.INSTANCE);
        addOperation(NotExpression.SINGLE);
        addOperation(NotExpression.DOUBLE);
        addOperation(LogicExpression.AND);
        addOperation(LogicExpression.OR);
        addOperation(LogExpression.STDOUT);
        addOperation(MapExpression.INSTANCE);
        addOperation(FilterExpression.INSTANCE);
        addOperation(ReduceExpression.INSTANCE);
        addOperation(AllExpression.INSTANCE);
        addOperation(ArrayHasExpression.SOME);
        addOperation(ArrayHasExpression.NONE);
        addOperation(MergeExpression.INSTANCE);
        addOperation(InExpression.INSTANCE);
        addOperation(ConcatenateExpression.INSTANCE);
        addOperation(SubstringExpression.INSTANCE);
        addOperation(MissingExpression.ALL);
        addOperation(MissingExpression.SOME);
    }

    public static boolean truthy(Object value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Boolean) {
            return (boolean) value;
        }

        if (value instanceof Number) {
            if (value instanceof Double) {
                Double d = (Double) value;

                if (d.isNaN()) {
                    return false;
                } else if (d.isInfinite()) {
                    return true;
                }
            }

            if (value instanceof Float) {
                Float f = (Float) value;

                if (f.isNaN()) {
                    return false;
                } else if (f.isInfinite()) {
                    return true;
                }
            }

            return ((Number) value).doubleValue() != 0.0;
        }

        if (value instanceof String) {
            return !((String) value).isEmpty();
        }

        if (value instanceof Collection) {
            return !((Collection) value).isEmpty();
        }

        if (value.getClass().isArray()) {
            return Array.getLength(value) > 0;
        }

        return true;
    }

    public JsonLogic addOperation(String name, Function<Object[], Object> function) {
        return addOperation(new PreEvaluatedArgumentsExpression() {
            @Override
            public Object evaluate(List arguments, Object data) {
                return function.apply(arguments.toArray());
            }

            @Override
            public String key() {
                return name;
            }
        });
    }

    /**
     * Add new operations http://jsonlogic.com/add_operation.html
     *
     * @param expression the operator to be added
     */
    public JsonLogic addOperation(JsonLogicExpression expression) {
        expressions.add(expression);
        evaluator = null;
        return this;
    }

    /**
     * Apply logic on data and get a result for all supported operations
     * http://jsonlogic.com/operations.html
     *
     * @param logic the logic as a json encoded string
     * @param data the data as a json encoded string
     * @return evaluation result
     */

    public Object apply(String logic, String data) throws JsonLogicException {
        return evaluate(logic, Utils.parse(data));
    }

    /**
     * Apply logic on data and get a result (json string only)
     *
     * @param logic the logic
     * @param data the data
     * @return evaluation result
     */
    public Object apply(String logic, Object data) throws JsonLogicException {
        return evaluate(logic, data);
    }

    private Object evaluate(String json, Object data) throws JsonLogicException {
        if (!parseCache.containsKey(json)) {
            parseCache.put(json, JsonLogicParser.parse(json));
        }

        if (evaluator == null) {
            evaluator = new JsonLogicEvaluator(expressions);
        }

        return evaluator.evaluate(parseCache.get(json), data);
    }
}
