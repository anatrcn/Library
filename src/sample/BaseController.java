package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteCollection;

import java.io.IOException;
import java.util.Objects;

import static org.dizitart.no2.Document.createDocument;
import static org.dizitart.no2.filters.Filters.eq;

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

        /*// Create a Nitrite Collection
        NitriteCollection collection = db.getCollection("users");
        Document doc = createDocument("username", "Ana").put("password", "password");

        // insert the document
        collection.insert(doc);

        // Create a Nitrite Collection
        NitriteCollection collection2 = db.getCollection("books");
        Document book1 = createDocument("id",1).put("title", "Ion").put("author", "Liviu Rebreanu").put("borrowedBy", "null");
        Document book2 = createDocument("id",2).put("title", "Moara cu Noroc").put("author", "Ioan Slavici").put("borrowedBy", "null");
        Document book3 = createDocument("id",3).put("title", "Luceafarul").put("author", "Mihai Eminescu").put("borrowedBy", "null");
        Document book4 = createDocument("id",4).put("title", "Romeo si Julieta").put("author", "William Shakespeare").put("borrowedBy", "null");

        // insert the books
        collection2.insert(book1);
        collection2.insert(book2);
        collection2.insert(book3);
        collection2.insert(book4);*/

        BaseController.db = db;
    }

    public Cursor getUserCursor(String field){
        Cursor cursor = db.getCollection("users").find(eq("username", field));
        return cursor;
    }

    public Cursor getBookCursor(){
        Cursor cursor = db.getCollection("books").find();
        return cursor;
    }


}
