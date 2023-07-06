package client.command.impl;

import client.ClientArguments;
import client.command.ClientCommand;
import com.google.gson.JsonObject;

public class ExitCommand implements ClientCommand {

    @Override
    public JsonObject databaseRequest(ClientArguments clientArguments) {
        var request = new JsonObject();
        request.addProperty("type", clientArguments.getType());
        return request;

    }
}
