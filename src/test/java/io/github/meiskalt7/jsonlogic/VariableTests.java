package io.github.meiskalt7.jsonlogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.gson.Gson;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class VariableTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testEmptyString() throws JsonLogicException {
    assertEquals("3.14", jsonLogic.apply("{\"var\": \"\"}", "3.14"));
  }

  @Test
  void testMapAccess() throws JsonLogicException {
    Map<String, Object> data = new HashMap<>();
    data.put("pi", 3.14);

    assertEquals(3.14, jsonLogic.apply("{\"var\": \"pi\"}", new Gson().toJson(data)));
  }

  @Test
  void testDefaultValue() throws JsonLogicException {
    assertEquals(3.14, jsonLogic.apply("{\"var\": [\"pi\", 3.14]}", null));
  }

  @Test
  void testUndefined() throws JsonLogicException {
    assertNull(jsonLogic.apply("{\"var\": [\"pi\"]}", null));
    assertNull(jsonLogic.apply("{\"var\": \"\"}", null));
    assertNull(jsonLogic.apply("{\"var\": 0}", null));
  }

  @Test
  void testArrayAccess() throws JsonLogicException {
    String data = new Gson().toJson(new String[] {"hello", "world"});

    assertEquals("hello", jsonLogic.apply("{\"var\": 0}", data));
    assertEquals("world", jsonLogic.apply("{\"var\": 1}", data));
  }

  @Test
  void testListAccess() throws JsonLogicException {
    String data = new Gson().toJson(Arrays.asList("hello", "world"));

    assertEquals("hello", jsonLogic.apply("{\"var\": 0}", data));
    assertEquals("world", jsonLogic.apply("{\"var\": 1}", data));
  }

  @Test
  void testComplexAccess() throws JsonLogicException {
    Map<String, Object> data = new HashMap<>();
    Map<String, Object> john = new HashMap<>();
    john.put("name", "John");
    john.put("followers", 1337);
    Map<String, Object> jane = new HashMap<>();
    jane.put("name", "Jane");
    jane.put("followers", 2048);
    data.put("users", Arrays.asList(john, jane));

    assertEquals("John", jsonLogic.apply("{\"var\": \"users.0.name\"}", data));
    assertEquals(1337.0, jsonLogic.apply("{\"var\": \"users.0.followers\"}", data));
    assertEquals("Jane", jsonLogic.apply("{\"var\": \"users.1.name\"}", data));
    assertEquals(2048.0, jsonLogic.apply("{\"var\": \"users.1.followers\"}", data));
  }
}
