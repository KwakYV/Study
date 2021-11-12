package com.geekbrains.handlers;

import com.geekbrains.model.AuthMessage;
import com.geekbrains.model.CommandType;
import com.geekbrains.model.ResponseMessage;
import com.geekbrains.util.ServerUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

@Slf4j
public class AuthMessageHandler extends SimpleChannelInboundHandler<AuthMessage> {
    private String user;
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AuthMessage authMessage) throws Exception {
        log.info("Checking if user is authorized and can proceed");
        this.user = authMessage.getLogin();
        Path userDir = Path.of("root").resolve(authMessage.getLogin());

        Path auth = userDir.resolve("auth.dat");
        String password = authMessage.getPassword();
        if (!Files.exists(userDir)){
            Files.createDirectory(userDir);
            Files.createFile(auth);
            Files.write(auth, password.getBytes(StandardCharsets.UTF_8));
        }

        String checkPassword = new String(Files.readAllBytes(auth), StandardCharsets.UTF_8);

        if (password.equals(checkPassword)){

            channelHandlerContext.writeAndFlush(new ResponseMessage("User is authorized",
                    authMessage.getLogin(),
                    CommandType.AUTH_OK_CMD,
                    ServerUtils.hierarchyMap(userDir),
                    null));
        } else {
            channelHandlerContext.writeAndFlush(new ResponseMessage("User is not authorized",
                    authMessage.getLogin(),
                    CommandType.AUTH_FAIL_CMD,
                    ServerUtils.hierarchyMap(userDir),
                    null));
        }

    }
}
