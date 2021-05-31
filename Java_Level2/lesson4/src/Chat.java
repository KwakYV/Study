import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Chat{
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField messageField;
    @FXML
    private ListView<String> userList;

     public void sendMessage(ActionEvent actionEvent) {
        chatArea.appendText(messageField.getText() + "\n");
        messageField.clear();
    }

    public void enterMessage(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)){
            chatArea.appendText(messageField.getText() + "\n");
            messageField.clear();
        }
    }
}
