package com.geekbrains.io;

import com.geekbrains.io.controller.AuthController;
import com.geekbrains.io.controller.StorageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

public class CloudStorageClient extends Application {
    public static  CloudStorageClient INSTANCE;

    private static final String STORAGE_WINDOW_FXML = "storage.fxml";
    private static final String AUTH_WINDOW_FXML = "authDialog.fxml";

    private Stage primaryStage;
    private Stage authStage;
    private FXMLLoader storageWindowLoader;
    private FXMLLoader authWindowLoader;

    Path clientDir;


    @Override
    public void init() throws Exception {
        INSTANCE = this;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        initWindows();
        Parent parent = FXMLLoader.load(getClass().getResource("storage.fxml"));
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }

    private void initWindows() throws Exception{
        initClientDir();
        initStorageWindow();
        initAuthWindow();
        getPrimaryStage().show();
        getAuthStage().show();
        getAuthController().initMessageHandler();
    }

    private void initAuthWindow() throws Exception{
        authWindowLoader = new FXMLLoader();
        authWindowLoader.setLocation(CloudStorageClient.class.getResource(AUTH_WINDOW_FXML));
        Parent authDialogPanel = authWindowLoader.load();

        authStage = new Stage();
        authStage.initOwner(primaryStage);
        authStage.initModality(Modality.WINDOW_MODAL);
        authStage.setScene(new Scene(authDialogPanel));
        authStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
    }

    public void initStorageWindow() throws Exception{
        storageWindowLoader = new FXMLLoader();
        storageWindowLoader.setLocation(CloudStorageClient.class.getResource(STORAGE_WINDOW_FXML));

        Parent root = storageWindowLoader.load();
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

//        setStageForSecondScreen(primaryStage);
    }

    public void initClientDir() throws IOException {
        clientDir = Path.of("default");
        if (!Files.exists(clientDir)){
            Files.createDirectory(clientDir);
        }
    }

    public Path getClientDir(){
        return clientDir;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getAuthStage() {
        return authStage;
    }

    private AuthController getAuthController(){
        return authWindowLoader.getController();
    }

    private StorageController getStorageController(){
        return storageWindowLoader.getController();
    }

    public void switchToMainChatWindow(String username, HashMap<String, List<String>> hierarchy) throws IOException {
        getPrimaryStage().setTitle(username);
        getAuthStage().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
