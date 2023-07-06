package client.databaseclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface DatabaseClientCommand {
    void execute(DataInputStream dataInputStream, DataOutputStream dataOutputStream);
}
