package client.command;

import client.ClientArguments;

public class CommandInvoker {
    private ClientCommand clientCommand;

    public void setClientCommand(ClientCommand clientCommand) {
        this.clientCommand = clientCommand;
    }

    public void execute(ClientArguments clientArguments) {
        this.clientCommand.execute(clientArguments);
    }
}
