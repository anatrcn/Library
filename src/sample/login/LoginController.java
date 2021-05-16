package sample.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import sample.BaseController;

import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    TextField usernameLogin;
    @FXML
    PasswordField passwordLogin;
    @FXML
    Label errorField;


    public void goToRegisterScreen(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        try {
            loadWindow("../register/register.fxml", "Registration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(ActionEvent actionEvent) {
        Cursor cursor = getUserCursor(usernameLogin.getText());

        for (Document document : cursor) {
            String username = (String) document.get("username");
            if (username.equalsIgnoreCase(usernameLogin.getText())) {
                ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                try {
                    loadWindow("../bookborrowing/bookborrowing.fxml", "Welcome " + usernameLogin.getText() + " to Book Borrowing");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        errorField.setText("Login Failed!");
    }


}
