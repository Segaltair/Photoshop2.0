package Controller.Coding;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.ResourceBundle;

public class InitializeListeners implements Initializable {
    @FXML
    TextField lexiconTextField;
    @FXML
    TextField phraseTextField;
    @FXML
    TextArea tableTextArea;
    @FXML
    TextArea codingTextArea;
    @FXML
    TextArea decodingTextArea;
    @FXML
    GridPane gridPane;
    @FXML
    Button codingButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lexiconTextField.setText("aeiou!");
        phraseTextField.setText("eaii!");

        codingButton.setOnAction(event -> {
            Encryption encryption = new Encryption(lexiconTextField.getText(), phraseTextField.getText());
            encryption.coding();
            encryption.decoding(encryption.getCoding() ,encryption.getTable());

            tableTextArea.setText(encryption.getTableReply().toString());
            codingTextArea.setText(encryption.getCodingReply().toString());
            decodingTextArea.setText(encryption.getDecodingReply().toString());
        });
    }
}