package ru.gb.java2.chat.server.chat;

import ru.gb.java2.chat.clientserver.Command;
import ru.gb.java2.chat.clientserver.CommandType;
import ru.gb.java2.chat.clientserver.commands.AuthCommandData;
import ru.gb.java2.chat.clientserver.commands.PrivateMessageCommandData;
import ru.gb.java2.chat.clientserver.commands.PublicMessageCommandData;
import ru.gb.java2.chat.clientserver.commands.UpdateNickNameCommand;
import ru.gb.java2.chat.server.chat.dao.UserDao;
import ru.gb.java2.chat.server.chat.dao.UserDaoImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;

public class ClientHandler {

    private final MyServer server;
    private final Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String username;

    public ClientHandler(MyServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    public void handle(ExecutorService service) throws IOException {
        inputStream = new ObjectInputStream(clientSocket.getInputStream());
        outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        service.execute(() -> {
            try {
                authentication();
                readMessages();
            } catch (IOException e) {
                System.err.println("Failed to process message from client");
            } catch(SQLException sqlErr) {
                sqlErr.printStackTrace();
            }finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Failed to close connection");
                }
            }
        });
    }

    private void authentication() throws IOException {

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Server closed connection");
                try {
                    sendCommand(Command.timeOutCommand("Время авторизации вышло!"));
                    closeConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, 20000);

        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }

            if (command.getType() == CommandType.AUTH) {
                AuthCommandData data = (AuthCommandData) command.getData();
                String login    = data.getLogin();
                String password = data.getPassword();

                String username = server.getAuthService().getUsernameByLoginAndPassword(login, password);
                if (username == null) {
                    sendCommand(Command.errorCommand("Некорректные логин и пароль!"));
                } else if (server.isUsernameBusy(username)) {
                    sendCommand(Command.errorCommand("Такой юзер уже существует!"));
                } else {
                    timer.cancel();
                    this.username = username;
                    sendCommand(Command.authOkCommand(username));
                    server.subscribe(this);
                    return;
                }
            }
        }
    }



    private Command readCommand() throws IOException {
        Command command = null;
        try {
            command = (Command) inputStream.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to read Command class");
            e.printStackTrace();
        }
        return command;
    }

    private void closeConnection() throws IOException {
        server.unsubscribe(this);
        clientSocket.close();
    }

    private void readMessages() throws IOException, SQLException {
        while (true) {
            Command command = readCommand();
            if (command == null) {
                continue;
            }

            switch (command.getType()) {
                case END:
                    return;
                case PRIVATE_MESSAGE: {
                    PrivateMessageCommandData data = (PrivateMessageCommandData) command.getData();
                    String recipient = data.getReceiver();
                    String privateMessage = data.getMessage();
                    server.sendPrivateMessage(this, recipient, privateMessage);
                    break;
                }
                case PUBLIC_MESSAGE: {
                    PublicMessageCommandData data = (PublicMessageCommandData) command.getData();
                    processMessage(data.getMessage());
                }
                case UPDATE_NICKNAME: {
                    UpdateNickNameCommand data = (UpdateNickNameCommand) command.getData();
                    UserDao dao = new UserDaoImpl();
                    dao.updateNickName(data.getSender(), data.getNickName());
                    this.username = data.getNickName();
                    server.notifyClientsUsersListUpdated();
                }
            }
        }
    }

    private void processMessage(String message) throws IOException {
        server.broadcastMessage(message, this);
    }

    public void sendCommand(Command command) throws IOException {
        outputStream.writeObject(command);
    }

    public String getUsername() {
        return username;
    }
}
