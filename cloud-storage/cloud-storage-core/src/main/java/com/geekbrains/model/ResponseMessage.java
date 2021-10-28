package com.geekbrains.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseMessage implements Serializable {
    String message;
    String user;
    CommandType type;
    public ResponseMessage(String message, String user, CommandType type){
        this.message = message;
        this.type = type;
        this.user = user;
    }

}
