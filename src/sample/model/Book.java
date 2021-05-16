package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Book {
    private SimpleIntegerProperty id;
    private SimpleStringProperty title;
    private SimpleStringProperty author;
    private SimpleStringProperty borrowed;
    private SimpleStringProperty borrowedDate;
    private SimpleStringProperty returnDate;

    public Book(int id, String title, String author, String borrowed,String borrowedDate, String returnDate) {
        this.id = new SimpleIntegerProperty(id);
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.borrowed = new SimpleStringProperty(borrowed);
        this.borrowedDate = new SimpleStringProperty(borrowedDate);
        this.returnDate = new SimpleStringProperty(returnDate);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getBorrowed() {
        return borrowed.get();
    }

    public void setBorrowed(String borrowed) {
        this.borrowed.set(borrowed);
    }

    public String getReturnDate() {
        return returnDate.get();
    }

    public void setReturnDate(String returnDate) {
        this.returnDate.set(returnDate);
    }

    public String getBorrowedDate() {
        return borrowedDate.get();
    }

    public SimpleStringProperty borrowedDateProperty() {
        return borrowedDate;
    }

    public void setBorrowedDate(String borrowedDate) {
        this.borrowedDate.set(borrowedDate);
    }
}
