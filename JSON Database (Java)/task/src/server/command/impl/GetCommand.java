package server.command.impl;

import com.google.gson.JsonObject;
import server.command.ServerCommand;
import server.database.JsonDatabase;

public class GetCommand implements ServerCommand {
    private final String key;

    public GetCommand(String key) {
        this.key = key;
    }

    @Override
    public JsonObject execute() {
        var response = new JsonObject();
        var dbOptionalValue = JsonDatabase.getInstance().get(this.asJsonArray(this.key));
        dbOptionalValue.ifPresentOrElse(
                value -> {
                    response.addProperty("response", "OK");
                    response.add("value", value);
                },
                () -> {
                    response.addProperty("response", "ERROR");
                    response.addProperty("reason", "No such key");
                }
        );
        return response;
    }
}
