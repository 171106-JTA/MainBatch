package me.daxterix.trms.servlet;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import java.util.List;

public class ServletUtils {
    public static String stringsToJsonArrayString(List<String> strings) {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (String s : strings)
            arrBuilder.add(s);
        JsonArray arr = arrBuilder.build();
        return arr.toString();
    }
}
