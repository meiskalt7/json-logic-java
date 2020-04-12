package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IfExpressionTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testIfTrue() throws JsonLogicException {
    String json = "{\"if\" : [true, \"yes\", \"no\"]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals("yes", result);
  }

  @Test
  void testIfFalse() throws JsonLogicException {
    String json = "{\"if\" : [false, \"yes\", \"no\"]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals("no", result);
  }

  @Test
  void testIfElseIfElse() throws JsonLogicException {
    String json = "{\"if\" : [\n" +
                  "  {\"<\": [50, 0]}, \"freezing\",\n" +
                  "  {\"<\": [50, 100]}, \"liquid\",\n" +
                  "  \"gas\"\n" +
                  "]}";
    Object result = jsonLogic.apply(json, null);

    assertEquals("liquid", result);
  }
}
