import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sample.BaseController;

import static org.dizitart.no2.filters.Filters.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class RegisterControllerTest {
    @Start
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample/register/register.fxml"));
        primaryStage.setTitle("Book-borrowing");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
    }

    @Test
    public void registerFailedUserAlreadyExists(FxRobot robot) {
        Button btnLogin = robot.lookup("#submitRegistration").queryAs(Button.class);
        assertNotNull(btnLogin);

        robot.clickOn("#usernameField");
        robot.write("Ana");
        robot.clickOn("#passwordField");
        robot.write("password");
        robot.clickOn("#repeatPasswordField");
        robot.write("password");
        robot.clickOn("#submitRegistration");

        Label label = robot.lookup("#userNameLabel").queryAs(Label.class);
        String errorText = label.getText();

        assertEquals("user already exists", errorText);
    }

    @Test
    public void registerFailedPasswordsDoNotMatchExists(FxRobot robot) {
        Nitrite db = BaseController.db;
        Cursor cursor = db.getCollection("users").find(eq("username", "TestUser"));
        for (Document document : cursor) {
            db.getCollection("users").remove(document);
        }

        Button btnLogin = robot.lookup("#submitRegistration").queryAs(Button.class);
        assertNotNull(btnLogin);

        robot.clickOn("#usernameField");
        robot.write("TestUser");
        robot.clickOn("#passwordField");
        robot.write("pass");
        robot.clickOn("#repeatPasswordField");
        robot.write("password");
        robot.clickOn("#submitRegistration");

        Label labelPassword = robot.lookup("#passwordLabel").queryAs(Label.class);
        String passwordErrorText = labelPassword.getText();
        Label labelRepeatPassword = robot.lookup("#repeatPasswordLabel").queryAs(Label.class);
        String repeatPasswordErrorText = labelRepeatPassword.getText();

        assertEquals("Password does not match", passwordErrorText);
        assertEquals("Repeat Password does not match", repeatPasswordErrorText);
    }

    @Test
    public void registrationSuccessTest(FxRobot robot) {
        Nitrite db = BaseController.db;
        Cursor cursor = db.getCollection("users").find(eq("username", "TestUser"));
        for (Document document : cursor) {
            db.getCollection("users").remove(document);
        }

        robot.clickOn("#usernameField");
        robot.write("TestUser");
        robot.clickOn("#passwordField");
        robot.write("password");
        robot.clickOn("#repeatPasswordField");
        robot.write("password");
        robot.clickOn("#submitRegistration");

        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        assertNotNull(btnLogin);

        cursor = db.getCollection("users").find(eq("username", "TestUser"));
        for (Document document : cursor) {
            db.getCollection("users").remove(document);
        }

    }
}
