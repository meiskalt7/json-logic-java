package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MathExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testAdd() throws JsonLogicException {
    String json = "{\"+\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(6.0, result);
  }

  @Test
  void testMultiAdd() throws JsonLogicException {
    String json = "{\"+\":[2,2,2,2,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(10.0, result);
  }

  @Test
  void testSingleAdd() throws JsonLogicException {
    String json = "{\"+\" : \"3.14\"}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(3.14, result);
  }

  @Test
  void testSubtract() throws JsonLogicException {
    String json = "{\"-\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(2.0, result);
  }

  @Test
  void testSingleSubtract() throws JsonLogicException {
    String json = "{\"-\": 2 }";
    Object result = jsonLogic.apply(json, null);

    assertEquals(-2.0, result);
  }

  @Test
  void testMultiply() throws JsonLogicException {
    String json = "{\"*\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(8.0, result);
  }

  @Test
  void testMultiMultiply() throws JsonLogicException {
    String json = "{\"*\":[2,2,2,2,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(32.0, result);
  }

  @Test
  void testDivide() throws JsonLogicException {
    String json = "{\"/\":[4,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(2.0, result);
  }

  @Test
  void testDivideBy0() throws JsonLogicException {
    String json = "{\"/\":[4,0]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(Double.POSITIVE_INFINITY, result);
  }

  @Test
  void testModulo() throws JsonLogicException {
    String json = "{\"%\": [101,2]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(1.0, result);
  }

  @Test
  void testMin() throws JsonLogicException {
    String json = "{\"min\":[1,2,3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(1.0, result);
  }

  @Test
  void testMax() throws JsonLogicException {
    String json = "{\"max\":[1,2,3]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals(3.0, result);
  }

  @Test
  void testDivideSingleNumber() throws JsonLogicException {
    assertEquals(null, jsonLogic.apply("{\"/\": [0]}", null));
  }
}
