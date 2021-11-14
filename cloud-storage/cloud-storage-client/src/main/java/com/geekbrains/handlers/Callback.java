package com.geekbrains.handlers;

import com.geekbrains.model.ResponseMessage;

import java.io.IOException;

public interface Callback {
    void callback(ResponseMessage message) throws IOException;
}
