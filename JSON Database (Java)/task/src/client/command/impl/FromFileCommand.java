package client.command.impl;

import client.ClientArguments;
import client.command.ClientCommand;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FromFileCommand implements ClientCommand {
    private static final String COMMAND_FILE_DIRECTORY = "C://workspace/hyperskills/JSON Database (Java)/JSON Database (Java)/task/src/client/data";

    @Override
    public JsonObject databaseRequest(ClientArguments clientArguments) {
        var commandFilePath = Paths.get(COMMAND_FILE_DIRECTORY, clientArguments.getInFile());
        var gson = new Gson();
        var request = new JsonObject();
        try {
            var jsonStringCommand = Files.readString(commandFilePath);
            var jsonObject = gson.fromJson(jsonStringCommand, JsonObject.class);
            request.add("type", jsonObject.get("type"));
            request.add("key", jsonObject.get("key"));
            request.add("value", jsonObject.get("value"));
        } catch (Exception e) {
            e.printStackTrace();
            request.addProperty("type", clientArguments.getType());
        }
        return request;
    }
}
