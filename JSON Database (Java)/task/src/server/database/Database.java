package server.database;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.Optional;

public interface Database {
    boolean set(JsonArray key, JsonElement value);

    Optional<JsonElement> get(JsonArray key);

    boolean delete(JsonArray key);
}
