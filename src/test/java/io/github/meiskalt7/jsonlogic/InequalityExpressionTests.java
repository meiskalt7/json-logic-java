package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InequalityExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testDifferentValueSameType() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"!=\": [1, 2]}", null));
  }

  @Test
  void testSameValueDifferentType() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"!=\": [1.0, \"1\"]}", null));
  }
}
