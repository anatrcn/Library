import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(ApplicationExtension.class)
public class BookBorrowingControllerTest {
    @Start
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("sample/login/login.fxml"));
        primaryStage.setTitle("Book-borrowing");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
    }

    public void login(FxRobot robot){
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

    @Test
    public void borrowBookTest(FxRobot robot) {
        login(robot);
    }

    @Test
    public void borrowBookTest2(FxRobot robot) {
        login(robot);
    }
}
