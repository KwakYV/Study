package com.geekbrains.io.dialogs;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

import java.util.Optional;

public abstract class StorageDialog {

    private Alert.AlertType dialogType;
    private final String title;
    private final String type;
    private final String message;

    protected StorageDialog(Alert.AlertType dialogType, String title, String type, String message) {
        this.dialogType = dialogType;
        this.title = title;
        this.type = type;
        this.message = message;
    }

    public Optional<ButtonType> showAndWait(Window owner) {
        Alert alert = new Alert(dialogType);
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(type);
        alert.setContentText(message);
        return alert.showAndWait();
    }


}
