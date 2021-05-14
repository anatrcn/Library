package sample.bookborrowing;

import org.dizitart.no2.Cursor;
import org.dizitart.no2.Document;
import sample.BaseController;

public class BookBorrowingController extends BaseController {

    public void initTable(){
        System.out.println("text");
        Cursor cursor = getBookCursor();

        for (Document document : cursor) {
            String title = (String) document.get("title");

            System.out.println(title);
        }

    }

}
