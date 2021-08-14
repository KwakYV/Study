package ru.gb.java2.chat.server.chat.dao;

import java.sql.*;
import java.util.Properties;

public class UserDaoImpl implements UserDao{
    private static Connection conn;
    private static PreparedStatement statement;

    private static void connect() throws SQLException {
        Properties property = new Properties();
        property.setProperty("user", "yuriy");
        property.setProperty("password", "java3");
        conn =  DriverManager.getConnection("jdbc:postgresql://localhost:5432/chat", property);
    }

    @Override
    public String getNickName(String login) {
        try{
            connect();
            statement = conn.prepareStatement("select nickname from chat.users where login = ?");
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                return rs.getString("nickname");
            } else {
                return null;
            }
        }catch(SQLException sqlErr){
            sqlErr.printStackTrace();
            System.out.println("Some error happened");
        }finally {
            freeResources();
        }
        return null;
    }

    private static void freeResources() {
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException connErr) {
                connErr.printStackTrace();
            }
            try {
                statement.close();
            } catch (SQLException stmtErr) {
                stmtErr.printStackTrace();
            }
        }
    }

    @Override
    public boolean isAuthorized(String login, String password) {
        try {
            connect();
            statement = conn.prepareStatement("select 1 as isauthorized from chat.users where login = ? and password = ?");
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException err) {
            err.printStackTrace();
        } finally {
            freeResources();
        }
        return false;
    }

    @Override
    public void updateNickName(String login, String newNickName) throws SQLException {
        try {
            connect();
            statement = conn.prepareStatement("update chat.users set nickname = ? where login = ?");
            statement.setString(1, newNickName);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException err) {
            err.printStackTrace();
            throw err;
        }finally {
            freeResources();
        }
    }
}
