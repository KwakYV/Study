import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.Array;
import java.util.Arrays;


public class Chat{
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private ListView<String> userList;

    @FXML
    public void initialize(){
        userList.setItems(FXCollections.observableArrayList(Arrays.asList("username1", "username2", "username3")));
    }

     public void sendMessage(ActionEvent actionEvent) {
         String message = prepareMessage();
         chatArea.appendText(message);
         messageField.clear();
     }

    private String prepareMessage() {
        String message = "";
        if (userList.getSelectionModel().getSelectedItem() == null){
            message = messageField.getText() + "\n";
        } else {
            String user = userList.getSelectionModel().getSelectedItem();
            message = user +": "+ messageField.getText() + "\n";
        }
        return message;
    }

    public void enterMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            String message = prepareMessage();
            chatArea.appendText(message);
            messageField.clear();
        }
    }
}
