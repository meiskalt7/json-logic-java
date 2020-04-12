package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NotExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testSingleBoolean() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"!\": false}", null));
  }

  @Test
  void testSingleNumber() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"!\": 0}", null));
  }

  @Test
  void testSingleString() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"!\": \"\"}", null));
  }

  @Test
  void testSingleArray() throws JsonLogicException {
    assertEquals(true, jsonLogic.apply("{\"!\": []}", null));
  }

  @Test
  void testDoubleBoolean() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"!!\": false}", null));
  }

  @Test
  void testDoubleNumber() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"!!\": 0}", null));
  }

  @Test
  void testDoubleString() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"!!\": \"\"}", null));
  }

  @Test
  void testDoubleArray() throws JsonLogicException {
    assertEquals(false, jsonLogic.apply("{\"!!\": [[]]}", null));
  }
}
