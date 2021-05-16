import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class LoginControllerTest {
    @Start
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("sample/login/login.fxml"));
        primaryStage.setTitle("Book-borrowing");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
    }

    @Test
    public void loginFailedTest(FxRobot robot) {
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        assertNotNull(btnLogin);

        robot.clickOn("#usernameLogin");
        robot.write("bbbbb");
        robot.clickOn("#passwordLogin");
        robot.write("aaaaa");
        robot.clickOn("#btnLogin");

        Label label = robot.lookup("#errorField").queryAs(Label.class);
        String errorText = label.getText();

        assertEquals("Login Failed!", errorText);
    }

    @Test
    public void loginSuccessTest(FxRobot robot) {
        Button btnLogin = robot.lookup("#btnLogin").queryAs(Button.class);
        assertNotNull(btnLogin);

        robot.clickOn("#usernameLogin");
        robot.write("Ana");
        robot.clickOn("#passwordLogin");
        robot.write("password");
        robot.clickOn("#btnLogin");

        Button borrowBook = robot.lookup("#borrowBook").queryAs(Button.class);
        assertNotNull(borrowBook);
    }
}
