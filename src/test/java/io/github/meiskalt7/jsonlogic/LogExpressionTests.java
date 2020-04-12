package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LogExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testDoesLog() throws JsonLogicException {
    assertEquals("hello world", jsonLogic.apply("{\"log\": \"hello world\"}", null));
  }
}
