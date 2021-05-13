package sample.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class RegisterController {

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    PasswordField repeatPasswordField;
    @FXML
    Label passwordLabel;
    @FXML
    Label repeatPasswordLabel;

    public void registerUser(ActionEvent actionEvent) {
        if (passwordField.getText().equals(repeatPasswordField.getText())) {
            passwordLabel.setText("");
            repeatPasswordLabel.setText("");
        } else {
            passwordLabel.setText("Password does not match");
            repeatPasswordLabel.setText("Repeat Password does not match");
        }
    }
}
