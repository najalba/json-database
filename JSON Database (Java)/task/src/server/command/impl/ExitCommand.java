package server.command.impl;

import com.google.gson.JsonObject;
import server.command.ServerCommand;

public class ExitCommand implements ServerCommand {
    @Override
    public JsonObject execute() {
        var response = new JsonObject();
        response.addProperty("response", "OK");
        return response;
    }
}
