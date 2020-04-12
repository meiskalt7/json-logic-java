package io.github.meiskalt7.jsonlogic.utils;

import com.google.gson.Gson;
import java.util.List;
import java.util.Map;

public class Utils {

    public static final Object parse(String value) {
        try {
            return new Gson().fromJson(value, Map.class);
        } catch (Exception e) {
            try {
                return new Gson().fromJson(value, List.class);
            } catch (Exception ex) {
                return value;
            }
        }
    }
}
