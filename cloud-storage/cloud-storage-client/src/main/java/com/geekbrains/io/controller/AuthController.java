package com.geekbrains.io.controller;

import com.geekbrains.handlers.AuthResponseHandler;
import com.geekbrains.io.dialogs.Dialogs;
import com.geekbrains.io.network.Server;
import com.geekbrains.model.AuthMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AuthController {

    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button authButton;
    private String login;
    private String password;

    @FXML
    public void executeAuth(ActionEvent actionEvent) {
        String login = loginField.getText();
        String password = passwordField.getText();
        this.login = login;
        this.password = password;
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            Dialogs.AuthError.EMPTY_CREDENTIALS.show();
            return;
        }

        if (!connectToServer()) {
            Dialogs.NetworkError.SERVER_CONNECT.show();
            return;
        }

        try {
            AuthMessage message = new AuthMessage(login, password);
            Server.getInstance().sendAuthMessage(message);
        } catch (Exception e) {
            Dialogs.NetworkError.SEND_MESSAGE.show();
            e.printStackTrace();
        }
    }

    private boolean connectToServer() {
        Server server = getServer();
        return server.isConnected() || server.connect();
    }

    private Server getServer() {
        return Server.getInstance();
    }


    public void initMessageHandler() {
        getServer().getChannel().pipeline().addLast(new AuthResponseHandler());
    }

    public void close() {
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
