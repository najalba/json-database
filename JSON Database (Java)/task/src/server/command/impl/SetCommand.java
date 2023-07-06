package server.command.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import server.command.ServerCommand;
import server.database.JsonDatabase;

public class SetCommand implements ServerCommand {
    private final String key;
    private final String value;

    public SetCommand(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public JsonObject execute() {
        var response = new JsonObject();
        if (JsonDatabase.getInstance().set(this.asJsonArray(this.key), JsonParser.parseString(value.matches("\\{.*}") ? value : "\"%s\"".formatted(value)))) {
            response.addProperty("response", "OK");
        } else {
            response.addProperty("response", "ERROR");
        }
        return response;
    }
}
