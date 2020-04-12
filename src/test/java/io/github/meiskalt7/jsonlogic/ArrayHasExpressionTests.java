package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayHasExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testSomeEmptyArray() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"some\": [[], {\">\": [{\"var\": \"\"}, 0]}]}", null));
  }

  @Test
  void testSomeAll() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"some\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 3]}]}", null));
    assertEquals(true, jsonLogic.apply("{\"some\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 1]}]}", null));
  }

  @Test
  void testNoneEmptyArray() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"some\": [[], {\">\": [{\"var\": \"\"}, 0]}]}", null));
  }

  @Test
  void testNoneAll() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"none\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 3]}]}", null));
    assertEquals(false, jsonLogic.apply("{\"none\": [[1, 2, 3], {\">\": [{\"var\": \"\"}, 2]}]}", null));
  }
}
