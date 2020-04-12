package io.github.meiskalt7.jsonlogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MissingExpressionTests {

    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    void testMissing() throws JsonLogicException {
        Map<String, Object> data = new HashMap<>();
        data.put("a", "apple");
        data.put("c", "carrot");
        Object result = jsonLogic.apply("{\"missing\": [\"a\", \"b\"]}", new Gson().toJson(data));

        assertEquals(1, ((List) result).size());
        assertEquals("b", ((List) result).get(0));
    }

    @Test
    void testMissingSomeUnderThreshold() throws JsonLogicException {
        Map<String, Object> data = new HashMap<>();
        data.put("a", "apple");
        data.put("c", "carrot");
        Object result = jsonLogic
            .apply("{\"missing_some\": [1, [\"a\", \"b\"]]}", new Gson().toJson(data));

        assertEquals(0, ((List) result).size());
    }

    @Test
    void testMissingSomeOverThreshold() throws JsonLogicException {
        Map<String, Object> data = new HashMap<>();
        data.put("a", "apple");
        Object result = jsonLogic
            .apply("{\"missing_some\": [2, [\"a\", \"b\", \"c\"]]}", new Gson().toJson(data));

        assertEquals(2, ((List) result).size());
        assertEquals("b", ((List) result).get(0));
        assertEquals("c", ((List) result).get(1));
    }

    @Test
    void testMissingSomeComplexExpression() throws JsonLogicException {
        Map<String, Object> data = new HashMap<>();
        data.put("first_name", "Bruce");
        data.put("last_name", "Wayne");
        String json = "{\"if\" :[\n" +
            "  {\"merge\": [\n" +
            "    {\"missing\":[\"first_name\", \"last_name\"]},\n" +
            "    {\"missing_some\":[1, [\"cell_phone\", \"home_phone\"] ]}\n" +
            "  ]},\n" +
            "  \"We require first name, last name, and one phone number.\",\n" +
            "  \"OK to proceed\"\n" +
            "]}";
        Object result = jsonLogic.apply(json, new Gson().toJson(data));

        assertEquals("We require first name, last name, and one phone number.", result);
    }
}
