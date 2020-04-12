package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogicExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testOr() throws JsonLogicException {
    assertEquals("a", jsonLogic.apply("{\"or\": [0, false, \"a\"]}", null));
  }

  @Test
  void testAnd() throws JsonLogicException {
    assertEquals("", jsonLogic.apply("{\"and\": [true, \"\", 3]}", null));
  }
}
