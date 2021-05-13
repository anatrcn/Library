package sample.login;

import com.sun.javafx.css.Style;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.BaseController;

import java.io.IOException;

public class LoginController extends BaseController {
    public void goToRegisterScreen(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
        try {
            loadWindow("../register/register.fxml", "Registration");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
