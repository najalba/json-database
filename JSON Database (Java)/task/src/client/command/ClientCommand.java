package client.command;

import client.ClientArguments;
import client.databaseclient.DatabaseClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public interface ClientCommand {
    default void execute(ClientArguments clientArguments) {
        var databaseClient = new DatabaseClient();
        var request = new Gson().toJson(this.databaseRequest(clientArguments));
        var response = databaseClient.execute(request);
        System.out.println("Sent: " + request);
        System.out.println("Received: " + response);
    }

    default String keyAsJsonString(String key) {
        var json = JsonParser.parseString(key);
        if (json.isJsonPrimitive()) {
            return key;
        } else if (json.isJsonArray()) {
            return new Gson().toJson(json);
        } else {
            throw new IllegalArgumentException("keys must by provided as primitive or array");
        }
    }

    JsonObject databaseRequest(ClientArguments clientArguments);
}
