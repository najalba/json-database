package server;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Server {
    public static final String ADDRESS = "127.0.0.1";
    public static final int PORT = 2000;
    private boolean running = true;

    public void run() {
        System.out.println("Server started!");
        var executorService = Executors.newFixedThreadPool(5);
        try (ServerSocket serverSocket = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS))) {
            while (running) {
                executorService.submit(new ServerRequestHandler(serverSocket.accept(), this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    public void stopRunning() {
        this.running = false;
    }

    public boolean isRunning() {
        return this.running;
    }
}
