package com.geekbrains.netty;

import com.geekbrains.model.AbstractMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<AbstractMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AbstractMessage abstractMessage) throws Exception {
      log.debug("Received {}", abstractMessage);
      channelHandlerContext.writeAndFlush(abstractMessage);
    }
}
