package server.command;

import com.google.gson.JsonObject;

public class CommandInvoker {
    private ServerCommand serverCommand;

    public void setServerCommand(ServerCommand serverCommand) {
        this.serverCommand = serverCommand;
    }

    public JsonObject execute() {
        return this.serverCommand.execute();
    }
}
