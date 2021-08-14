package ru.gb.java2.chat.clientserver.commands;

import java.io.Serializable;

public class UpdateNickNameCommand implements Serializable {
    private String sender;
    private String nickName;

    public UpdateNickNameCommand(String sender, String nickName){
        this.sender = sender;
        this.nickName = nickName;
    }

    public String getSender() {
        return sender;
    }

    public String getNickName() {
        return nickName;
    }
}
