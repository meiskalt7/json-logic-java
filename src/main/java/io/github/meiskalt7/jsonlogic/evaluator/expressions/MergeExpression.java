package io.github.meiskalt7.jsonlogic.evaluator.expressions;

import io.github.meiskalt7.jsonlogic.utils.ArrayLike;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MergeExpression implements PreEvaluatedArgumentsExpression {

    public static final MergeExpression INSTANCE = new MergeExpression();

    private MergeExpression() {
        // Use INSTANCE instead.
    }

    @Override
    public String key() {
        return "merge";
    }

    @Override
    public Object evaluate(List arguments, Object data) {
        return ((List<Object>) arguments).stream()
            .map(obj -> ArrayLike.isEligible(obj) ? new ArrayLike(obj) : Collections.singleton(obj))
            .map(Collection::stream)
            .flatMap(Function.identity())
            .collect(Collectors.toList());
    }
}
