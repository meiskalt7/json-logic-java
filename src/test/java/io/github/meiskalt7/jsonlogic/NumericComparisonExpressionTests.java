package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NumericComparisonExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testLessThan() throws JsonLogicException {
    String json = "{\"<\" : [1, 2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  void testLessThanOrEqual() throws JsonLogicException {
    String json = "{\"<=\" : [1, 1]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  void testGreaterThan() throws JsonLogicException {
    String json = "{\">\" : [2, 1]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  void testGreaterThanOrEqual() throws JsonLogicException {
    String json = "{\">=\" : [1, 1]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  void testBetweenExclusive() throws JsonLogicException {
    String json = "{\"<\" : [1, 2, 3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }

  @Test
  void testBetweenInclusive() throws JsonLogicException {
    String json = "{\"<=\" : [1, 1, 3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(true, result);
  }
}
