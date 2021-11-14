package com.geekbrains.handlers;

import com.geekbrains.model.CommandType;
import com.geekbrains.model.FileMessage;
import com.geekbrains.model.ResponseMessage;
import com.geekbrains.util.ServerUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


@Slf4j
public class FileMessageHandler extends SimpleChannelInboundHandler<FileMessage> {
    private Path userDir;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FileMessage fileMessage) throws Exception {
        String user = fileMessage.getLogin();
        userDir = Path.of("root").resolve(user);

        if (fileMessage.getType().equals(CommandType.FILE_UPLOAD_CMD)){
            Path file = userDir.resolve(fileMessage.getFileName());
            if (!Files.exists(file)){
                Files.createFile(file);
            }
            RandomAccessFile raf = new RandomAccessFile(file.toFile(), "rw");
            raf.seek(raf.length());
            raf.write(fileMessage.getBytes());
            raf.close();

            channelHandlerContext.writeAndFlush(new ResponseMessage("File has been saved",
                    "user",
                    CommandType.FILE_UPLOAD_CMD,
                    ServerUtils.hierarchyMap(userDir),
                    null));
        }

        if (fileMessage.getType().equals(CommandType.FILE_DOWNLOAD_CMD)){
            Path file = userDir.resolve(fileMessage.getFilePath());
            RandomAccessFile raf = new RandomAccessFile(file.toFile(), "r");
            FileChannel fileChannel = raf.getChannel();

            int bufferSize = 1024;
            if (bufferSize > fileChannel.size()){
                bufferSize = (int) fileChannel.size();
            }

            ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
            while (fileChannel.read(buffer) > 0){
                FileMessage message = new FileMessage(file.getFileName().toString(),
                        file.toString(),
                        Files.size(file),
                        Arrays.copyOfRange(buffer.array(), 0, buffer.position()));
                message.setType(CommandType.FILE_DOWNLOAD_CMD);
                message.setLogin(user);
                buffer.clear();
                channelHandlerContext.writeAndFlush(message);
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("we are here");
        cause.printStackTrace();
    }
}
