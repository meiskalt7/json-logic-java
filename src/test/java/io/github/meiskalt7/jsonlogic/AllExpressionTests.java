package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AllExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testEmptyArray() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"all\": [[], {\">\": [{\"var\": \"\"}, 0]}]}", null));
  }

  @Test
  void testAll() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"all\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 0]}]}", null));
    assertEquals(false, jsonLogic.apply("{\"all\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 1]}]}", null));
  }
}
