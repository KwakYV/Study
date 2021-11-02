package com.geekbrains.io;

import com.geekbrains.io.controller.AuthController;
import com.geekbrains.io.controller.StorageController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

    public void switchToMainChatWindow(String username, HashMap<String, List<String>> hierarchy) {
        getPrimaryStage().setTitle(username);
        getStorageController().initMessageHandler(hierarchy);
        getAuthController().close();
        getAuthStage().close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
