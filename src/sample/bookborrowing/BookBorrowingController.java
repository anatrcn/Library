package sample.bookborrowing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import sample.BaseController;
import sample.model.Book;

public class BookBorrowingController extends BaseController {

    @FXML
    Button loadBooks;

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

    public ObservableList<Book> list = FXCollections.observableArrayList();


    public void initTable() {
        System.out.println("text");
        Cursor cursor = getBookCursor();

        for (Document document : cursor) {
            int id = (Integer) document.get("id");
            String title = (String) document.get("title");
            String author = (String) document.get("author");
            String borrowed = (String) document.get("borrowed");

            Book book = new Book(id, title, author, borrowed);
            list.add(book);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        borrowed.setCellValueFactory(new PropertyValueFactory<>("borrowed"));
        bookTableView.setItems(list);

    }

}
