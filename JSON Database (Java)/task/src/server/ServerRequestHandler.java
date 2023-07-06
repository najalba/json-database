package server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import server.command.CommandInvoker;
import server.command.impl.DeleteCommand;
import server.command.impl.ExitCommand;
import server.command.impl.GetCommand;
import server.command.impl.SetCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Objects;

public class ServerRequestHandler implements Runnable {
    private final Socket socket;
    private final Server server;

    public ServerRequestHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try (var input = new DataInputStream(socket.getInputStream());
             var output = new DataOutputStream(socket.getOutputStream());
        ) {
            var gson = new Gson();
            var request = gson.fromJson(input.readUTF(), JsonObject.class);
            var typeJson = request.get("type");
            var type = typeJson.getAsString();
            var keyJson = request.get("key");
            var key = keyJson instanceof JsonPrimitive ? keyJson.getAsString() : gson.toJson(keyJson);
            var valueJson = request.get("value");
            var value = Objects.nonNull(valueJson) ? valueJson instanceof JsonPrimitive ? valueJson.getAsString() : gson.toJson(valueJson) : null;
            var commandInvoker = new CommandInvoker();
            switch (type) {
                case "get" -> commandInvoker.setServerCommand(new GetCommand(key));
                case "set" -> commandInvoker.setServerCommand(new SetCommand(key, value));
                case "delete" -> commandInvoker.setServerCommand(new DeleteCommand(key));
                case "exit" -> commandInvoker.setServerCommand(new ExitCommand());
            }
            output.writeUTF(gson.toJson(commandInvoker.execute()));
            this.socket.close();
            if ("exit".equals(type) && this.server.isRunning()) {
                this.shutdownServer();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shutdownServer() {
        this.server.stopRunning();
        try (Socket socket = new Socket(Server.ADDRESS, Server.PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            var exitRequest = new JsonObject();
            exitRequest.addProperty("type", "exit");
            output.writeUTF(new Gson().toJson(exitRequest));
            input.readUTF();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
