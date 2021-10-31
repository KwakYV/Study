package com.geekbrains.io.controller;

import com.geekbrains.io.CloudStorageClient;
import com.geekbrains.io.network.Server;
import com.geekbrains.model.FileMessage;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server = Server.getInstance();
        try{
            server.connect();
        } catch (Exception e){
            log.error("Failed to connect on server", e);
        }
    }

    @FXML
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
            server.getChannel().writeAndFlush(message);
        }
        input.setText("File has been send");
        log.info("File has been send");
    }

    public void initMessageHandler(HashMap<String, List<String>> hierarchy) {
        this.filesMap = hierarchy;
        Platform.runLater(() -> input.setText("initMessageHandler"));
        input.setText("Without platform.runlater");
        System.out.println(input.getText());
//        Platform.runLater(() -> {
//            if (hierarchy.size() > 0) {
//                System.out.println("We are in initMessage and size is not 0");
//                root = new TreeItem<>(hierarchy.get("root").get(0));
//                for (String key : hierarchy.keySet()) {
//                    if (!key.equals("root")){
//                        // If current key is root then just elements to root node
//                        if (key.equals(root.getValue())){
//                            for (String value : hierarchy.get(key)) {
//                                root.getChildren().add(new TreeItem<>(value));
//                            }
//                        }else {
//                            //Check if node already created and added to root node
//                            if (root.getChildren().contains(key)) {
//                                TreeItem<String> child = root.getChildren().get(root.getChildren().indexOf(key));
//                                for (String value : hierarchy.get(key)) {
//                                    child.getChildren().add(new TreeItem<>(value));
//                                }
//                            } else {
//                                System.out.println("check");
//                            }
//                        }
//                    }
//                }
//                System.out.println("check if root is null - " + (root == null));
//                listView.setRoot(root);
//            }
//        });
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

    public TreeView<String> getListView() {
        return listView;
    }

    @FXML
    public void download(ActionEvent actionEvent) {
        System.out.println(root == null);
        System.out.println(input.getText());
        listView.setRoot(root);
    }

}
