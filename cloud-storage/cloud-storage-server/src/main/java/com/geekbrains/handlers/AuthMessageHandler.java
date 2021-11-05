package com.geekbrains.handlers;

import com.geekbrains.model.AuthMessage;
import com.geekbrains.model.CommandType;
import com.geekbrains.model.ResponseMessage;
import com.geekbrains.util.ServerUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class AuthMessageHandler extends SimpleChannelInboundHandler<AuthMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AuthMessage authMessage) throws Exception {
        log.info("Checking if user is authorized and can proceed");

        // TODO - Implement authorization here !!!

        Path userDir = Path.of("root").resolve(authMessage.getLogin());
        if (!Files.exists(userDir)){
            Files.createDirectory(userDir);
        }

        channelHandlerContext.writeAndFlush(new ResponseMessage("User is authorized",
                authMessage.getLogin(),
                CommandType.AUTH_OK_CMD,
                ServerUtils.hierarchyMap(userDir),
                null));
    }
}
