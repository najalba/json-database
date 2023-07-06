package server.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public interface ServerCommand {
    JsonObject execute();

    default JsonArray asJsonArray(String key) {
        var jsonElement = JsonParser.parseString(key);
        if (jsonElement.isJsonPrimitive()) {
            var jsonArray = new JsonArray();
            jsonArray.add(jsonElement);
            return jsonArray;
        } else if (jsonElement.isJsonArray()) {
            return jsonElement.getAsJsonArray();
        } else {
            throw new IllegalArgumentException("Key must be only primitive or array");
        }
    }
}
