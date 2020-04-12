package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubstringExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  final void testSubstringSingleArg() throws JsonLogicException {
    assertEquals("logic", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 4]}", null));
  }

  @Test
  void testSubstringSingleNegativeArg() throws JsonLogicException {
    assertEquals("logic", jsonLogic.apply("{\"substr\": [\"jsonlogic\", -5]}", null));
  }

  @Test
  void testSubstringDoubleArg() throws JsonLogicException {
    assertEquals("son", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 1, 3]}", null));
  }

  @Test
  void testSubstringDoubleNegativeArg() throws JsonLogicException {
    assertEquals("log", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 4, -2]}", null));
  }

  @Test
  void testSubstringSingleArgOutOfBounds() throws JsonLogicException {
    assertEquals("", jsonLogic.apply("{\"substr\": [\"jsonlogic\", -40]}", null));
  }

  @Test
  void testSubstringDoubleArgOutOfBounds() throws JsonLogicException {
    assertEquals("", jsonLogic.apply("{\"substr\": [\"jsonlogic\", 20, -40]}", null));
  }
}
