package sample.bookborrowing;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import static org.dizitart.no2.Document.createDocument;
import static org.dizitart.no2.filters.Filters.eq;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import sample.BaseController;
import sample.model.Book;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import static sample.login.LoginController.loggedUser;

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
    @FXML
    private Label totalFine;
    @FXML
    private TextField returnDateValue;
    @FXML
    private Button returnBook;
    @FXML
    private Button borrowBook;
    @FXML
    private Label errorDate;
    @FXML
    private Label errorFine;

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

        totalFine.setText(String.valueOf(loggedUser.getTotalFine()));
    }

    @FXML
    public void clickItem() {
        System.out.println(bookTableView.getSelectionModel().getSelectedItem().getId());
        System.out.println(bookTableView.getSelectionModel().getSelectedItem().getAuthor());
        System.out.println(bookTableView.getSelectionModel().getSelectedItem().getTitle());
        bookTableView.refresh();
    }

    @FXML
    public void returnBook() {
        String borrowedBookUser = bookTableView.getSelectionModel().getSelectedItem().getBorrowed();

        if (borrowedBookUser.equals(loggedUser.getUsername())) {
            int id = bookTableView.getSelectionModel().getSelectedItem().getId();
            bookTableView.getSelectionModel().getSelectedItem().setBorrowed("not borrowed");
            db.getCollection("books").update(eq("id", id), createDocument("borrowed", "not borrowed"));

            bookTableView.getSelectionModel().getSelectedItem().setReturnDate(new Date().toString());
            bookTableView.refresh();
        }
    }

    @FXML
    public void borrowBook() {
        String borrowedBookUser = bookTableView.getSelectionModel().getSelectedItem().getBorrowed();

        if (borrowedBookUser.equals("not borrowed") || borrowedBookUser.isEmpty()) {
            String enteredDate = returnDateValue.getText();

            try {
                new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss").parse(enteredDate);
                bookTableView.getSelectionModel().getSelectedItem().setReturnDate(enteredDate);
            } catch (ParseException e) {
                errorDate.setText("Invalid Date");
                return;
            }

            errorDate.setText("Valid Date");
            bookTableView.getSelectionModel().getSelectedItem().setBorrowed(loggedUser.getUsername());

            int id = bookTableView.getSelectionModel().getSelectedItem().getId();
            db.getCollection("books").update(eq("id", id), createDocument("borrowed", loggedUser.getUsername()));

            bookTableView.refresh();
        }

    }
}
