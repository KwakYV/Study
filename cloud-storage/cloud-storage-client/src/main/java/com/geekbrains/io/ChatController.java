package com.geekbrains.io;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class ChatController implements Initializable {

    public ListView<String> listView;
    public TextField input;
    private DataInputStream dis;
    private DataOutputStream dos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = dis.readUTF();
                        Platform.runLater(() -> listView.getItems().add(message));
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
        StringBuilder localPath = new StringBuilder(input.getText());
        File file = new File(localPath.toString());
        Long fileSize = file.length();
        byte[] buffer = new byte[8192];
        localPath.append("@SEP@").append(fileSize.toString());
        localPath.append("@SEP@").append(file.getName());
        /**
         * Send to server path of the file
         */
        dos.writeUTF(localPath.toString());

        InputStream fileStream = new FileInputStream(file);
        int count;
        while ((count = fileStream.read(buffer)) > 0){
            dos.write(buffer, 0, count);
        }
        dos.flush();
        fileStream.close();
        input.clear();
    }
}
