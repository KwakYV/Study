package ru.gb.java2.chat.server.chat.auth;

import ru.gb.java2.chat.server.chat.dao.UserDao;
import ru.gb.java2.chat.server.chat.dao.UserDaoImpl;

public class AuthService {

    public String getUsernameByLoginAndPassword(String login, String password) {
        UserDao dao = new UserDaoImpl();
        if (dao.isAuthorized(login, password)){
            return dao.getNickName(login);
        }
        return null;
    }
}
