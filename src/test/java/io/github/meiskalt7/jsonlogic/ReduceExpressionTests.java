package io.github.meiskalt7.jsonlogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

public class ReduceExpressionTests {

    private static final JsonLogic jsonLogic = new JsonLogic();

    @Test
    void testReduce() throws JsonLogicException {
        String json = "{\"reduce\":[\n" +
            "    {\"var\":\"\"},\n" +
            "    {\"+\":[{\"var\":\"current\"}, {\"var\":\"accumulator\"}]},\n" +
            "    0\n" +
            "]}";
        int[] data = new int[]{1, 2, 3, 4, 5, 6};
        Object result = jsonLogic.apply(json, new Gson().toJson(data));

        assertEquals(21.0, result);
    }
}
