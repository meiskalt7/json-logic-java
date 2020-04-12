package io.github.meiskalt7.jsonlogic;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CustomOperationTests {
  private static final JsonLogic jsonLogic = new JsonLogic();

  @Test
  void testCustomOp() throws JsonLogicException {
    jsonLogic.addOperation("greet", args -> String.format("Hello %s!", args[0]));
    Assertions.assertEquals("Hello json-logic!", jsonLogic.apply("{\"greet\": [\"json-logic\"]}", null));
  }

  @Test
  void testCustomOpWithUppercaseLetter() throws JsonLogicException {
    jsonLogic.addOperation("Greet", args -> String.format("Hello %s!", args[0]));
    Assertions.assertEquals("Hello json-logic!", jsonLogic.apply("{\"Greet\": [\"json-logic\"]}", null));
  }
}
