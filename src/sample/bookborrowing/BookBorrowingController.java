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
    private TableColumn<Book, String> borrowedDate;
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
            String borrowedDate = (String) document.get("borrowedDate");
            String returnDate = (String) document.get("returnDate");

            //db.getCollection("books").remove(document);

            Book book = new Book(id, title, author, borrowed, borrowedDate, returnDate);
            list.add(book);
        }

        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        author.setCellValueFactory(new PropertyValueFactory<>("author"));
        borrowed.setCellValueFactory(new PropertyValueFactory<>("borrowed"));
        borrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
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
        errorDate.setText("");
        if (bookTableView.getSelectionModel().getSelectedItem() != null) {
            String borrowedBookUser = bookTableView.getSelectionModel().getSelectedItem().getBorrowed();

            if (borrowedBookUser.equals(loggedUser.getUsername())) {
                int id = bookTableView.getSelectionModel().getSelectedItem().getId();
                bookTableView.getSelectionModel().getSelectedItem().setBorrowed("not borrowed");
                db.getCollection("books").update(eq("id", id), createDocument("borrowed", "not borrowed"));

                String pattern = "yyyy-MM-dd:hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String currentDate = simpleDateFormat.format(new Date());
                db.getCollection("books").update(eq("id", id), createDocument("returnDate", currentDate));
                bookTableView.getSelectionModel().getSelectedItem().setReturnDate(currentDate);

                String promisedDateString = bookTableView.getSelectionModel().getSelectedItem().getReturnDate();
                try {
                    Date promisedDate = simpleDateFormat.parse(promisedDateString);
                    long promisedMills = promisedDate.getTime();
                    long currentDateMillis = new Date().getTime();

                    if(currentDateMillis > promisedMills){
                        double totalFineVal = Double.parseDouble(totalFine.getText());
                        totalFine.setText(String.valueOf(totalFineVal + 0.1));
                        db.getCollection("users").update(eq("username", loggedUser), createDocument("totalFine", totalFineVal));
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                bookTableView.refresh();
            } else if (borrowedBookUser.equals("not borrowed")) {
                errorDate.setText("Cannot return this book");
            }
        } else {
            errorDate.setText("Select a book!");
        }
    }

    @FXML
    public void borrowBook() {
        errorDate.setText("");
        if (bookTableView.getSelectionModel().getSelectedItem() != null) {
            String borrowedBookUser = bookTableView.getSelectionModel().getSelectedItem().getBorrowed();

            if (borrowedBookUser.equals("not borrowed") || borrowedBookUser.isEmpty()) {
                String enteredDate = returnDateValue.getText();

                try {
                    Date convertedDate = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss").parse(enteredDate);
                    bookTableView.getSelectionModel().getSelectedItem().setReturnDate(enteredDate);

                    if (convertedDate.before(new Date())) {
                        errorDate.setText("Date must not be in the past");
                        return;
                    }
                } catch (ParseException e) {
                    errorDate.setText("Invalid Date");
                    return;
                }

                errorDate.setText("Valid Date");
                bookTableView.getSelectionModel().getSelectedItem().setBorrowed(loggedUser.getUsername());

                String pattern = "yyyy-MM-dd:hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String date = simpleDateFormat.format(new Date());

                int id = bookTableView.getSelectionModel().getSelectedItem().getId();
                bookTableView.getSelectionModel().getSelectedItem().setBorrowedDate(date);
                db.getCollection("books").update(eq("id", id), createDocument("borrowedDate", date)).getAffectedCount();
                db.getCollection("books").update(eq("id", id), createDocument("borrowed", loggedUser.getUsername()));

                bookTableView.refresh();
            } else {
                errorDate.setText("Book already borrowed");
            }
        } else {
            errorDate.setText("Select a book!");
        }
    }
}
