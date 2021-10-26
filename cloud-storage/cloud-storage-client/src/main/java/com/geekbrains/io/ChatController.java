package com.geekbrains.io;

import com.geekbrains.handlers.ResponseHandler;
import com.geekbrains.model.FileMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.*;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ResourceBundle;

@Slf4j
public class ChatController implements Initializable {


    public ListView<String> listView;
    public TextField input;
    private ObjectDecoderInputStream dis;
    private ObjectEncoderOutputStream dos;
    private ByteArrayEncoder bytesEnc;

    private static final String HOST = "localhost";
    private static int PORT = 8189;
    private SocketChannel channel;
    private static int CHUNK_SIZE = 8192;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                                        new ResponseHandler()
                                );
                            }
                        });
                ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
                future.channel().closeFuture().await();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                workerGroup.shutdownGracefully();
            }
        });
        readThread.setDaemon(true);
        readThread.start();
    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {
        String filePath = input.getText();
        Path file = Path.of(filePath);
        if (!Files.exists(file)){
            log.info("Specified file does not exist on your machine");
            return;
        }
        Long fileSize = Files.size(file);
        String fileName = file.getFileName().toString();
        input.clear();

        RandomAccessFile raf = new RandomAccessFile(file.toFile(), "r");
        FileChannel fileChannel = raf.getChannel();

        int bufferSize = 1024;
        if (bufferSize > fileChannel.size()){
            bufferSize = (int) fileChannel.size();
        }

        ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
        while (fileChannel.read(buffer) > 0){
            FileMessage message = new FileMessage(fileName, filePath, fileSize, Arrays.copyOfRange(buffer.array(), 0, buffer.position()));
            buffer.clear();
            channel.writeAndFlush(message);
        }
        log.info("File has been send");
    }
}
