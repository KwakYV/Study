package com.geekbrains.netty;

import com.geekbrains.model.AbstractMessage;
import com.geekbrains.model.FileMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Slf4j
public class FileMessageHandler extends SimpleChannelInboundHandler<FileMessage> {
    private Path userDir;
    public FileMessageHandler(Path userDir) throws IOException {
        this.userDir = userDir;
        if (!Files.exists(userDir)){
            Files.createDirectory(userDir);
        }
    }
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FileMessage fileMessage) throws Exception {
        log.info("Received - " + fileMessage.getFileName());
        log.info("File path - " + fileMessage.getFilePath());
        log.info("File size - " + fileMessage.getFileSize());
        Path file = userDir.resolve(fileMessage.getFileName());
        if (!Files.exists(file)){
            Files.createFile(file);
        }
        FileOutputStream outStream = new FileOutputStream(file.toFile());
        outStream.write(fileMessage.getBytes());
        outStream.flush();
        outStream.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("we are here");
        cause.printStackTrace();
    }
}
