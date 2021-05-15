package sample.bookborrowing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import sample.BaseController;
import sample.model.Book;

import java.net.URL;
import java.util.ResourceBundle;

public class BookBorrowingController extends BaseController implements Initializable {
    @FXML
    TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, Integer> id;
    @FXML
    private TableColumn<Book, String> title;
    @FXML
    private TableColumn<Book, String> author;
    @FXML
    private TableColumn<Book, String> borrowed;
    @FXML
    private TableColumn<Book, String> returnDate;

    public ObservableList<Book> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Cursor cursor = getBookCursor();

        for (Document document : cursor) {
            int id = (Integer) document.get("id");
            String title = (String) document.get("title");
            String author = (String) document.get("author");
            String borrowed = (String) document.get("borrowed");
            String time = (String) document.get("time");

            //db.getCollection("books").remove(document);

            Book book = new Book(id, title, author, borrowed, time);
            list.add(book);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        borrowed.setCellValueFactory(new PropertyValueFactory<>("borrowed"));
        returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        bookTableView.setItems(list);
    }

    @FXML
    public void clickItem() {
           /* System.out.println(bookTableView.getSelectionModel().getSelectedItem().getId());
            System.out.println(bookTableView.getSelectionModel().getSelectedItem().getAuthor());
            System.out.println(bookTableView.getSelectionModel().getSelectedItem().getTitle());*/

        borrowed.setText("borrowed");

    }

}
