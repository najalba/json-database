package server.database;

import com.google.gson.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JsonDatabase implements Database {
    private static final JsonDatabase INSTANCE = new JsonDatabase();
    private final Path dbFilePath = Paths.get("C://workspace/hyperskills/JSON Database (Java)/JSON Database (Java)/task/src/server/data/db.json");
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private JsonDatabase() {
        if (!Files.exists(this.dbFilePath)) {
            try {
                Files.writeString(this.dbFilePath, new Gson().toJson(new JsonObject()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static JsonDatabase getInstance() {
        return JsonDatabase.INSTANCE;
    }

    @Override
    public boolean set(JsonArray key, JsonElement value) {
        var writeLock = this.readWriteLock.writeLock();
        writeLock.lock();
        try {
            var gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            JsonObject databaseContent = gson.fromJson(Files.readString(this.dbFilePath), JsonObject.class);
            JsonObject parentJsonObject = databaseContent;
            if (key.size() == 1) {
                parentJsonObject.remove(key.getAsString());
                parentJsonObject.add(key.getAsString(), value);
            } else if (key.size() > 1) {
                for (int i = 0; i < key.size() - 1; i++) {
                    var currentKey = key.get(i).getAsString();
                    if (parentJsonObject.has(currentKey)) {
                        parentJsonObject = parentJsonObject.getAsJsonObject(currentKey);
                    } else {
                        var jsonObject = new JsonObject();
                        parentJsonObject.add(currentKey, jsonObject);
                        parentJsonObject = jsonObject;
                    }
                }
                var lastKey = key.get(key.size() - 1).getAsString();
                parentJsonObject.remove(lastKey);
                parentJsonObject.add(lastKey, value);
            }
            Files.writeString(this.dbFilePath, gson.toJson(databaseContent));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Optional<JsonElement> get(JsonArray key) {
        var readLock = this.readWriteLock.readLock();
        readLock.lock();
        try {
            var iteratorKey = key.iterator();
            JsonElement value = new Gson().fromJson(Files.readString(this.dbFilePath), JsonElement.class);
            while (iteratorKey.hasNext() && Objects.nonNull(value)) {
                value = value.getAsJsonObject().get(iteratorKey.next().getAsString());
            }
            return Optional.ofNullable(value);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean delete(JsonArray key) {
        var writeLock = this.readWriteLock.writeLock();
        writeLock.lock();
        try {
            var gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            JsonObject databaseContent = gson.fromJson(Files.readString(this.dbFilePath), JsonObject.class);
            JsonObject parentJsonObject = databaseContent;
            JsonElement removedElement = null;
            if (key.size() == 1) {
                removedElement = parentJsonObject.remove(key.getAsString());
            } else if (key.size() > 1) {
                for (int i = 0; i < key.size() - 1 && Objects.nonNull(parentJsonObject); i++) {
                    parentJsonObject = parentJsonObject.get(key.get(i).getAsString()).getAsJsonObject();
                }
                if (Objects.nonNull(parentJsonObject)) {
                    removedElement = parentJsonObject.remove(key.get(key.size() - 1).getAsString());
                }
            }
            if (Objects.nonNull(removedElement)) {
                Files.writeString(this.dbFilePath, gson.toJson(databaseContent));
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            writeLock.unlock();
        }
    }
}
