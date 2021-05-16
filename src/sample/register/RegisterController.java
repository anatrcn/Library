package sample.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.NitriteCollection;
import sample.BaseController;

import java.io.IOException;

import static org.dizitart.no2.Document.createDocument;


public class RegisterController extends BaseController {

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
    @FXML
    Label userNameLabel;

    public void registerUser(ActionEvent actionEvent) {
        if (passwordField.getText().isEmpty()) {
            passwordLabel.setText("Password cannot be empty");
            return;
        }
        if (repeatPasswordField.getText().isEmpty()) {
            passwordLabel.setText("Repeat Password cannot be empty");
            return;
        }

        if (usernameField.getText().isEmpty()) {
            passwordLabel.setText("Username cannot be empty");
            return;
        }

        Cursor cursor = getUserCursor(usernameField.getText());
        for (Document document : cursor) {
            String username = (String) document.get("username");
            if (username.equalsIgnoreCase(usernameField.getText())) {
                userNameLabel.setText("user already exists");
                return;
            }
        }

        if (passwordField.getText().equals(repeatPasswordField.getText())) {
            passwordLabel.setText("");
            repeatPasswordLabel.setText("");

            String userName = usernameField.getText();
            String password = passwordField.getText();

            // Create a Nitrite Collection
            NitriteCollection collection = db.getCollection("users");
            Document doc = createDocument("username", userName)
                    .put("password", password).put("totalFine", 0.0);

            // insert the document
            collection.insert(doc);

        } else {
            passwordLabel.setText("Password does not match");
            repeatPasswordLabel.setText("Repeat Password does not match");
            return;
        }

        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        try {
            loadWindow("../login/login.fxml", "Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
