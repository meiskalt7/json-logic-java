package io.github.meiskalt7.jsonlogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

public class OfficialJsonLogicTests {

    private static final String testUrl = "http://jsonlogic.com/tests.json";

    private static final Gson gson = new GsonBuilder().create();

    private List<JsonLogicTest> officialTests() throws IOException {
        List<JsonLogicTest> officialTests = new ArrayList<>();
        Gson gson = new Gson();
        String category = "";
        for (Object object :
            gson.fromJson(
                new Scanner(new URL(testUrl).openStream(), StandardCharsets.UTF_8.name())
                    .useDelimiter("___").next(), //TODO fix it
                List.class)) {
            if (object instanceof String) {
                category = (String) object;
            } else if (object instanceof List) {
                List list = (List) object;
                officialTests.add(
                    new JsonLogicTest(
                        list.get(0) + ", " + list.get(1) + " " + category,
                        list.get(0), list.get(1), list.get(2)));
            }
        }
        return officialTests;
    }

    @TestFactory
    public Collection dynamicStringTests() throws IOException {
        return officialTests().stream().map(test ->
            DynamicTest.dynamicTest(test.title, () -> {
                System.out.println(test.title);
                assertEquals(
                    gson.toJson(test.expected)
                        .replace("\"", "")
                        .replace(" ", ""),
                    Objects.toString(
                        new JsonLogic().apply(gson.toJson(test.rule), gson.toJson(test.data)))
                        .replace("\"", "")
                        .replace(" ", "")
                );
            })).collect(Collectors.toList());
    }

    @TestFactory
    public Collection dynamicTests() throws IOException {
        return officialTests().stream().map(test ->
            DynamicTest.dynamicTest(test.title, () -> {
                System.out.println(test.title + " rule:" + test.rule + " data:" + test.data);
                assertEquals(
                    test.expected,
                    new JsonLogic().apply(gson.toJson(test.rule), test.data)
                );
            })).collect(Collectors.toList());
    }

    private static final class JsonLogicTest {

        private final String title;
        private final Object rule;
        private final Object data;
        private final Object expected;

        public JsonLogicTest(String title, Object rule, Object data, Object expected) {
            this.title = title;
            this.rule = rule;
            this.data = data;
            this.expected = expected;
        }

        public String toString() {
            return "JsonLogicTest(title=" + title + ", rule=" + rule + ", data="
                + data + ", expected=" + expected + ")";
        }
    }
}
