package com.geekbrains.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.AttributeKey;

import java.nio.file.Path;

public class ChunkedFileHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("IN Chunk");
        int length = msg.readableBytes();
        byte[] buffer = new byte[length];
        msg.readBytes(buffer);
        System.out.println(new String(buffer));
        System.out.println();

    }
}
