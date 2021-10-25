package com.geekbrains.io;

import com.geekbrains.model.AbstractMessage;
import com.geekbrains.model.FileMessage;
import io.netty.handler.codec.serialization.ObjectDecoderInputStream;
import io.netty.handler.codec.serialization.ObjectEncoderOutputStream;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.stream.ChunkedFile;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

@Slf4j
public class ChatController implements Initializable {


    public ListView<String> listView;
    public TextField input;
    private ObjectDecoderInputStream dis;
    private ObjectEncoderOutputStream dos;
    private ByteArrayEncoder bytesEnc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            dos = new ObjectEncoderOutputStream(socket.getOutputStream());
            dis = new ObjectDecoderInputStream(socket.getInputStream());

            bytesEnc = new ByteArrayEncoder();
            System.out.println("OK");
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        AbstractMessage message = (AbstractMessage) dis.readObject();
                        Platform.runLater(() -> listView.getItems().add(message.getMessage()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            readThread.setDaemon(true);
            readThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        InputStream fileStream = new FileInputStream(filePath);
        FileMessage message = new FileMessage(fileName, filePath, fileSize, fileStream.readAllBytes());

        dos.writeObject(message);
        fileStream.close();
        dos.flush();

    }
}
