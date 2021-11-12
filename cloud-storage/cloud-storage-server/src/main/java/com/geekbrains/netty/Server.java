package com.geekbrains.netty;

import com.geekbrains.handlers.AuthMessageHandler;
import com.geekbrains.handlers.FileMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class Server {
    private final Path root = Path.of("root");
    public Server() throws IOException {
        if (!Files.exists(root)){
            Files.createDirectory(root);
        }
        EventLoopGroup auth = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup();
        try{
            ServerBootstrap server = new ServerBootstrap();
            server.group(auth, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
//                                    new ChunkedWriteHandler(),
//                                    new ChunkedFileHandler(),
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                    new AuthMessageHandler(),
                                    new FileMessageHandler()

                            );
                        }
                    });
            ChannelFuture future = server.bind(8189).sync();
            log.debug("Server started...");
            future.channel().closeFuture().sync(); // block
        } catch (Exception e){
            log.error("Error: {}", e);
        } finally {
            auth.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
    }
}
