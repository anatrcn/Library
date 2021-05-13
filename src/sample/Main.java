package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import java.util.ArrayList;
import java.util.Date;

import static org.dizitart.no2.Document.createDocument;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login/login.fxml"));
        primaryStage.setTitle("Book-borrowing");
        primaryStage.setScene(new Scene(root, 600, 550));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
