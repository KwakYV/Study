package com.geekbrains.io.controller;

import com.geekbrains.io.CloudStorageClient;
import com.geekbrains.io.dialogs.Dialogs;
import com.geekbrains.io.network.Server;
import com.geekbrains.model.CommandType;
import com.geekbrains.model.FileMessage;
import com.geekbrains.model.ResponseMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static javafx.application.Platform.runLater;

@Slf4j
public class StorageController implements Initializable {

    @FXML
    TreeView<String> listView;
    @FXML
    public TextField input;

    private Server server;
    private final FileChooser fileChooser = new FileChooser();

    private TreeItem<String> root;
    private HashMap<String, List<String>> filesMap;
    private String login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            server = Server.getInstance(this::processMessage);
//            if (!server.isConnected()){
//                server.connect();
//            }
        } catch (Exception e){
            log.error("Failed to connect on server", e);
        }
    }

    private void processMessage(ResponseMessage message) throws IOException {
        if (message.getType().equals(CommandType.AUTH_OK_CMD)){
            this.login = message.getUser();
            runLater(() -> {
                try {
                    CloudStorageClient.INSTANCE.switchToMainChatWindow(message.getUser(),
                            message.getHierarchy());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        if (message.getType().equals(CommandType.AUTH_FAIL_CMD)){
            runLater(Dialogs.AuthError.INVALID_CREDENTIALS::show);
        }

        if (message.getType().equals(CommandType.FILE_UPLOAD_CMD)){
            log.info("File upload - " + message.getMessage());
        }

        if (message.getType().equals(CommandType.FILE_DOWNLOAD_CMD)){
            log.info("File Download cmd");
            downloadFile(message.getContent());
        }

    }

    private void downloadFile(byte[] content) throws IOException {
        TreeItem<String> item = listView.getSelectionModel().getSelectedItem();
        if (item.getChildren().size() > 0) {
            log.error("Choose file name instead of directory");
            return;
        } else {
            String fileName = item.getValue();
            Path filePath = CloudStorageClient.INSTANCE.getClientDir().resolve(fileName);

            if (!Files.exists(filePath)){
                Files.createDirectory(filePath);
            }

            RandomAccessFile raf = new RandomAccessFile(filePath.toFile(), "rw");
            raf.seek(raf.length());
            raf.write(content);
            raf.close();
        }
    }

    /**
     * Construct directory structure
     * @param hierarchy
     */
    private void displayDir(HashMap<String, List<String>> hierarchy) {
        if (hierarchy.size() > 0) {
                root = new TreeItem<>(hierarchy.get("root").get(0));
                for (String key : hierarchy.keySet()) {
                    if (!key.equals("root")){
                        // If current key is root then just elements to root node
                        if (key.equals(root.getValue())){
                            for (String value : hierarchy.get(key)) {
                                root.getChildren().add(new TreeItem<>(value));
                            }
                        }else {
                            //Check if node already created and added to root node
                            TreeItem<String> child = getItem(root, key);
                            for (String value : hierarchy.get(key)) {
                                child.getChildren().add(new TreeItem<>(value));
                            }
                        }
                    }
                }
                listView.setRoot(root);
            }
    }

    private TreeItem getItem(TreeItem<String> item, String value){
        if (item != null && item.getValue().equals(value)){
            return item;
        }

        for (TreeItem<String> child: item.getChildren()){
            TreeItem<String> tmp = getItem(child, value);
            if (tmp != null){
                return tmp;
            }

        }
        return null;
    }

    @FXML
    public void sendMessage(ActionEvent actionEvent) throws IOException {
        log.info(CloudStorageClient.INSTANCE.getUserName());
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
            message.setType(CommandType.FILE_UPLOAD_CMD);
            message.setLogin(CloudStorageClient.INSTANCE.getUserName());
            buffer.clear();
            server.getChannel().writeAndFlush(message);
        }
        log.info("File has been send");
    }


    @FXML
    public void chooseFile(ActionEvent actionEvent) throws Exception{
        // First let's clear input field
        input.clear();
        File file = fileChooser.showOpenDialog(CloudStorageClient.INSTANCE.getPrimaryStage());
        if (file != null) {
            if (Files.exists(Path.of(file.getAbsolutePath()))){
                input.setText(file.getAbsolutePath());
                sendMessage(actionEvent);
            }
        }

    }

    @FXML
    public void download(ActionEvent actionEvent) {
        String path = listView.getSelectionModel().getSelectedItem().getValue();
        FileMessage message = new FileMessage(
                "",
                path, 0L, null);
        message.setType(CommandType.FILE_DOWNLOAD_CMD);
        message.setLogin(this.login);
        server.getChannel().writeAndFlush(message);
    }

    public void initController(String username, HashMap<String, List<String>> hierarchy) {
        this.login = username;
        runLater(() -> displayDir(hierarchy));
    }
}
