package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcatenateExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testCat() throws JsonLogicException {
    assertEquals("hello world 2!", jsonLogic.apply("{\"cat\": [\"hello\", \" world \", 2, \"!\"]}", null));
    assertEquals("pi is 3.14159", jsonLogic.apply("{\"cat\": [\"pi is \", 3.14159]}", null));
  }
}
