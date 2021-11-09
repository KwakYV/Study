package com.geekbrains.handlers;

import com.geekbrains.io.CloudStorageClient;
import com.geekbrains.io.dialogs.Dialogs;
import com.geekbrains.model.CommandType;
import com.geekbrains.model.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

public class AuthResponseHandler  extends SimpleChannelInboundHandler<ResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage) throws Exception {
        if (responseMessage.getType().equals(CommandType.AUTH_OK_CMD)){
            Platform.runLater(() -> CloudStorageClient.INSTANCE.switchToMainChatWindow(responseMessage.getUser(),
                    responseMessage.getHierarchy()));
        }

        if (responseMessage.getType().equals(CommandType.AUTH_FAIL_CMD)){
            Platform.runLater(Dialogs.AuthError.INVALID_CREDENTIALS::show);
        }
    }
}
