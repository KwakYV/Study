package com.geekbrains.io.network;

import com.geekbrains.handlers.AuthResponseHandler;
import com.geekbrains.handlers.ResponseHandler;
import com.geekbrains.model.AuthMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.*;

public class Server {
    private static final int SERVER_PORT = 8189;
    private static final String SERVER_HOST = "localhost";

    private static Server INSTANCE;

    private final String host;
    private final int port;

    private ObjectDecoderInputStream dis;
    private ObjectEncoderOutputStream dos;

    private SocketChannel channel;

    private boolean isConnected;

    public static Server getInstance(){
        if (INSTANCE == null){
            INSTANCE =  new Server();
        }
        return INSTANCE;
    }

    private Server(String host, int port){
        this.host = host;
        this.port = port;
    }

    private Server(){
        this(SERVER_HOST, SERVER_PORT);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean connect(){
        Thread readThread = new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(
//                                        new ChunkedWriteHandler(),
                                        new ObjectDecoder(ClassResolvers.cacheDisabled(null)),
                                        new ObjectEncoder(),
                                        new AuthResponseHandler()
                                );
                            }
                        });
                ChannelFuture future = bootstrap.connect(SERVER_HOST, SERVER_PORT).sync();
                future.channel().closeFuture().await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        });
        readThread.setDaemon(true);
        readThread.start();
        isConnected = true;
        return true;
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void sendAuthMessage(AuthMessage message) {
        channel.writeAndFlush(message);
    }
}
