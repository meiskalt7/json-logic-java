package io.github.meiskalt7.jsonlogic;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testMap() throws JsonLogicException {
    String json = "{\"map\": [\n" +
                  "  {\"var\": \"\"},\n" +
                  "  {\"*\": [{\"var\": \"\"}, 2]}\n" +
                  "]}";
    int[] data = new int[] {1, 2, 3};
    Object result = jsonLogic.apply(json, new Gson().toJson(data));

    assertEquals(3, ((List) result).size());
    assertEquals(2.0, ((List) result).get(0));
    assertEquals(4.0, ((List) result).get(1));
    assertEquals(6.0, ((List) result).get(2));
  }
}
