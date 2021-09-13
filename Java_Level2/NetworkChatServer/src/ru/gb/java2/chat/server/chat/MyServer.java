package ru.gb.java2.chat.server.chat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.server.chat.auth.AuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {

    private final List<ClientHandler> clients = new ArrayList<>();
    private AuthService authService;

    private static final Logger LOGGER = LogManager.getLogger(MyServer.class.getName());

    private ExecutorService cachedService;

    public MyServer() {
        cachedService = Executors.newCachedThreadPool();
    }


    public void start(int port) {
        LOGGER.info(String.format("Starting server at port {}", port));
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            LOGGER.info("Server has been started");
            authService = new AuthService();
            while (true) {
                waitAndProcessNewClientConnection(serverSocket);
            }
        } catch (IOException e) {

            LOGGER.error(String.format("Failed to bind port {}", port));
            LOGGER.debug(e);

            System.err.println("Failed to bind port " + port);
            e.printStackTrace();
        }finally {
            cachedService.shutdown();

        }
    }

    private void waitAndProcessNewClientConnection(ServerSocket serverSocket) throws IOException {
        LOGGER.info("Waiting for new client connection...");
        Socket clientSocket = serverSocket.accept();
        LOGGER.info("Client has been connected");
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.handle(cachedService);
    }

    public synchronized void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), message));
            }
        }
    }

    public synchronized void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        notifyClientsUsersListUpdated();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        notifyClientsUsersListUpdated();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public synchronized boolean isUsernameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void sendPrivateMessage(ClientHandler sender, String recipient, String privateMessage) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender && client.getUsername().equals(recipient)) {
                client.sendCommand(Command.clientMessageCommand(sender.getUsername(), privateMessage));
                break;
            }
        }
    }

    protected void notifyClientsUsersListUpdated() throws IOException {
        List<String> users = new ArrayList<>();
        for (ClientHandler client : clients) {
            users.add(client.getUsername());
        }

        for (ClientHandler client : clients) {
            client.sendCommand(Command.updateUsersListCommand(users));
        }

    }

    public Logger getLogger(){
        return LOGGER;
    }
}
