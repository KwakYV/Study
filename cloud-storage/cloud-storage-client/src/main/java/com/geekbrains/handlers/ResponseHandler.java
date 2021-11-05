package com.geekbrains.handlers;

import com.geekbrains.model.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseHandler extends SimpleChannelInboundHandler<ResponseMessage> {
    private Callback callback;

    public ResponseHandler(Callback callback){
        this.callback = callback;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage) throws Exception {
        log.info("Response - " + responseMessage.getMessage());
        callback.callback(responseMessage);
    }
}
