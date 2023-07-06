package client.databaseclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DatabaseClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 2000;

    public String execute(String request) {
        var response = new StringBuilder();
        this.execute((input, output) -> {
            try {
                output.writeUTF(request);
                response.append(input.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return response.toString();
    }

    private void execute(DatabaseClientCommand databaseClientCommand) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            databaseClientCommand.execute(input, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
