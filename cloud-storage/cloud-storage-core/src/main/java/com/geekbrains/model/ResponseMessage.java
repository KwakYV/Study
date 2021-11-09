package com.geekbrains.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Data
public class ResponseMessage implements Serializable {
    String message;
    String user;
    CommandType type;
    HashMap<String, List<String>> hierarchy;
    public ResponseMessage(String message, String user, CommandType type,
                           HashMap<String, List<String>> hierarchy){
        this.message = message;
        this.type = type;
        this.user = user;
        this.hierarchy = hierarchy;
    }

}
