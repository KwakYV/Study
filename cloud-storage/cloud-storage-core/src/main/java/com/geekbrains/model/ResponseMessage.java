package com.geekbrains.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ResponseMessage implements Serializable {
    String message;
}
