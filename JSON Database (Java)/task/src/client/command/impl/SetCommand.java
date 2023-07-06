package client.command.impl;

import client.ClientArguments;
import client.command.ClientCommand;
import com.google.gson.JsonObject;

public class SetCommand implements ClientCommand {
    @Override
    public JsonObject databaseRequest(ClientArguments clientArguments) {
        var request = new JsonObject();
        request.addProperty("type", clientArguments.getType());
        request.addProperty("key", this.keyAsJsonString(clientArguments.getKey()));
        request.addProperty("value", clientArguments.getValue());
        return request;
    }
}
