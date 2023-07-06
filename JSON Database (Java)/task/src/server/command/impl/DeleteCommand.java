package server.command.impl;

import com.google.gson.JsonObject;
import server.command.ServerCommand;
import server.database.JsonDatabase;

public class DeleteCommand implements ServerCommand {
    private final String key;

    public DeleteCommand(String key) {
        this.key = key;
    }

    @Override
    public JsonObject execute() {
        var response = new JsonObject();
        if (JsonDatabase.getInstance().delete(this.asJsonArray(this.key))) {
            response.addProperty("response", "OK");
        } else {
            response.addProperty("response", "ERROR");
            response.addProperty("reason", "No such key");
        }
        return response;
    }
}
