package io.github.meiskalt7.jsonlogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class InExpressionTests {

    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    void testStringIn() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"in\": [\"race\", \"racecar\"]}", null));
    }

    @Test
    void testStringNotIn() throws JsonLogicException {
        assertEquals(false, jsonLogic.apply("{\"in\": [\"race\", \"clouds\"]}", null));
    }

    @Test
    void testArrayIn() throws JsonLogicException {
        assertEquals(true, jsonLogic.apply("{\"in\": [1, [1, 2, 3]]}", null));
        assertEquals(true, jsonLogic.apply("{\"in\": [4.56, [1, 2, 3, 4.56]]}", null));
    }

    @Test
    void testArrayNotIn() throws JsonLogicException {
        assertEquals(false, jsonLogic.apply("{\"in\": [5, [1, 2, 3]]}", null));
    }

    @Test
    void testInVariableInt() throws JsonLogicException {
        Map<String, Object> data = new HashMap<>();
        data.put("list", Arrays.asList(1, 2, 3));
        assertEquals(true, jsonLogic.apply("{\"in\": [2, {\"var\": \"list\"}]}", new Gson().toJson(data)));
    }

    @Test
    void testNotInVariableInt() throws JsonLogicException {
        Map<String, Object> data = new HashMap<>();
        data.put("list", Arrays.asList(1, 2, 3));
        assertEquals(false, jsonLogic.apply("{\"in\": [4, {\"var\": \"list\"}]}", new Gson().toJson(data)));
    }

    @Test
    void testAllVariables() throws JsonLogicException {
        Map data = Stream.of(new Object[][]{
            new Object[]{"list", Arrays.asList(1, 2, 3)},
            new Object[]{"value", 3}
        }).collect(Collectors.toMap(o -> o[0], o -> o[1]));

        assertEquals(true,
            jsonLogic.apply("{\"in\": [{\"var\": \"value\"}, {\"var\": \"list\"}]}", new Gson().toJson(data)));
    }

    @Test
    void testSingleArgument() throws JsonLogicException {
        assertFalse((boolean) jsonLogic.apply("{\"in\": [\"Spring\"]}", null));
    }

    @Test
    void testBadSecondArgument() throws JsonLogicException {
        assertFalse((boolean) jsonLogic.apply("{\"in\": [\"Spring\", 3]}", null));
    }
}
