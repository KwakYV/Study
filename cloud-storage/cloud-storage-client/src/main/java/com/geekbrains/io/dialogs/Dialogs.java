package com.geekbrains.io.dialogs;

import com.geekbrains.io.CloudStorageClient;
import javafx.scene.control.Alert;

public class Dialogs {

    public enum AuthError {
        EMPTY_CREDENTIALS("Логин и пароль должны быть указаны!"),
        INVALID_CREDENTIALS("Логин и пароль заданы некорректно!"),
        TIMEOUT("Время ожидания ввода пароля и логина вышло. Сервер закрывает соединение!"),
        ;

        private static final String TITLE = "Ошибка аутентификации";
        private static final String TYPE = TITLE;
        private final String message;

        AuthError(String message) {
            this.message = message;
        }

        public void show() {
            showDialog(Alert.AlertType.ERROR, TITLE, TYPE, message);
        }
    }

    public enum NetworkError {
        SEND_MESSAGE("Не удалось отправить сообщение!"),
        SERVER_CONNECT("Не удалось установить соединение с сервером!"),
        UPDATE_NICKNAME_ERROR("Не удалось обновить имя пользователя"),
        ;

        private static final String TITLE = "Сетевая ошибка";
        private static final String TYPE = "Ошибка передачи данных по сети";
        private final String message;

        NetworkError(String message) {
            this.message = message;
        }
        public void show() {
            showDialog(Alert.AlertType.ERROR, TITLE, TYPE, message);
        }

    }

    private static void showDialog(Alert.AlertType dialogType, String title, String type, String message) {
        Alert alert = new Alert(dialogType);
        alert.initOwner(CloudStorageClient.INSTANCE.getPrimaryStage());
        alert.setTitle(title);
        alert.setHeaderText(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
