package ru.gb.java2.chat.server.chat.dao;

import java.sql.SQLException;

public interface UserDao {
    public String getNickName(String login);
    public boolean isAuthorized(String login, String password);
    public void updateNickName(String login, String newNickName) throws SQLException;
}
