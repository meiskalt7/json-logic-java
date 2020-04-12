package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StrictEqualityExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testSameValueSameType() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"===\": [1, 1.0]}", null));
  }

  @Test
  void testSameValueDifferentType() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"===\": [1, \"1\"]}", null));
  }
}
