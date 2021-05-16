package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import java.io.IOException;

import static org.dizitart.no2.Document.createDocument;

public class BaseController {

    public static Nitrite db;

    static {
        init();
    }

    public void loadWindow(String location, String title) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(location));
        Scene scene = new Scene(root);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    private static void init(){
        Nitrite db = Nitrite.builder()
                .compressed()
                .filePath("db/book.db")
                .openOrCreate("user", "password");

        // Create a Nitrite Collection
        NitriteCollection collection = db.getCollection("users");
        Document doc = createDocument("username", "Ana")
                .put("password", "password");

        // insert the document
        collection.insert(doc);

        BaseController.db = db;
    }


}
