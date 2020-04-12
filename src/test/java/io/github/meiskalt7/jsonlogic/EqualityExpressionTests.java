package io.github.meiskalt7.jsonlogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EqualityExpressionTests {

    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    void testSameValueSameType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [1, 1]}", null));
    }

    @Test
    void testSameValueDifferentType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [1, 1]}", null));
    }

    @Test
    void testDifferentValueDifferentType() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [[], false]}", null));
    }

    @Test
    void testEmptyStringAndZeroComparison() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"==\": [\" \", 0]}", null));
    }

    @Test
    void testSameValueInVariable() throws JsonLogicException {
        assertEquals(
            true,
            jsonLogic.apply("{\"==\": [{\"var\": \"isFlag\"}, false]}", "{\"isFlag\": false}")
        );
    }
}
