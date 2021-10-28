package com.geekbrains.handlers;

import com.geekbrains.model.AuthMessage;
import com.geekbrains.model.CommandType;
import com.geekbrains.model.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthMessageHandler extends SimpleChannelInboundHandler<AuthMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AuthMessage authMessage) throws Exception {
        log.info("Checking if user is authorized and can proceed");
        channelHandlerContext.writeAndFlush(new ResponseMessage("User is authorized",
                authMessage.getLogin(),
                CommandType.AUTH_OK_CMD));
    }
}
