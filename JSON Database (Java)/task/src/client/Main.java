package client;

import client.command.CommandInvoker;
import client.command.impl.*;
import com.beust.jcommander.JCommander;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        var clientArguments = new ClientArguments();
        JCommander.newBuilder()
                .addObject(clientArguments)
                .build()
                .parse(args);
        var commandInvoker = new CommandInvoker();
        if (Objects.nonNull(clientArguments.getInFile())) {
            commandInvoker.setClientCommand(new FromFileCommand());
        } else {
            switch (clientArguments.getType()) {
                case "get" -> commandInvoker.setClientCommand(new GetCommand());
                case "set" -> commandInvoker.setClientCommand(new SetCommand());
                case "delete" -> commandInvoker.setClientCommand(new DeleteCommand());
                case "exit" -> commandInvoker.setClientCommand(new ExitCommand());
            }
        }
        commandInvoker.execute(clientArguments);
    }
}
