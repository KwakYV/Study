package com.geekbrains.model;

import lombok.Data;

@Data
public class AuthMessage extends AbstractMessage {
    private String password;


    public AuthMessage(String login, String password) {
        super();
        super.setLogin(login);
        this.password = password;
        setType(CommandType.AUTH_CMD);
    }
}
