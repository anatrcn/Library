package sample.register;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import static org.dizitart.no2.Document.createDocument;


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

        if (passwordField.getText().equals(repeatPasswordField.getText())) {
            passwordLabel.setText("");
            repeatPasswordLabel.setText("");

            String userName = usernameField.getText();
            String password = passwordField.getText();

            Nitrite db = Nitrite.builder()
                    .compressed()
                    .filePath("db/book.db")
                    .openOrCreate("user", "password");

            // Create a Nitrite Collection
            NitriteCollection collection = db.getCollection("users");
            Document doc = createDocument("username", userName)
                    .put("password", password);

            // insert the document
            collection.insert(doc);

        } else {
            passwordLabel.setText("Password does not match");
            repeatPasswordLabel.setText("Repeat Password does not match");
        }
    }
}
