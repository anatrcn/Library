import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public void borrowBookFailNoSelectedBookTest(FxRobot robot) {
        login(robot);

        robot.clickOn("#borrowBook");
        Label labelError = robot.lookup("#errorLabel").queryAs(Label.class);
        String errorText = labelError.getText();

        assertEquals("Select a book!", errorText);
    }

    @Test
    public void returnBookFailNoSelectedBookTest(FxRobot robot) {
        login(robot);

        robot.clickOn("#returnBook");
        Label labelError = robot.lookup("#errorLabel").queryAs(Label.class);
        String errorText = labelError.getText();

        assertEquals("Select a book!", errorText);
    }

    @Test
    public void returnBookFailSelectedBookInvalidDateTest(FxRobot robot) {
        login(robot);

        Table table = new Table(robot, "#bookTableView");
        table.clickOnNthTableRow(1);

        robot.clickOn("#returnBook");
        Label labelError = robot.lookup("#errorLabel").queryAs(Label.class);
        String errorText = labelError.getText();

        assertEquals("Cannot return this book", errorText);
    }

    @Test
    public void borrowAndReturnValidTest(FxRobot robot) {
        login(robot);

        Table table = new Table(robot, "#bookTableView");
        table.clickOnNthTableRow(1);

        robot.clickOn("#returnDateValue");
        String pattern = "yyyy-MM-dd:HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String currentDate = simpleDateFormat.format(new Date(new Date().getTime()+20000));
        robot.write(currentDate);

        robot.clickOn("#borrowBook");
        String borrowedText = table.getTextOnNthTableRowNthTableColum(1, 3);

        assertEquals("Ana", borrowedText);
        robot.clickOn("#returnBook");
        borrowedText = table.getTextOnNthTableRowNthTableColum(1, 3);
        assertEquals("not borrowed", borrowedText);
    }
}
