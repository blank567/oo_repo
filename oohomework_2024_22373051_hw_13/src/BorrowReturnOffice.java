import com.oocourse.library1.LibraryBookId;

import java.util.ArrayList;

public class BorrowReturnOffice {
    private ArrayList<LibraryBookId> booksInBorrowReturnOffice;

    public BorrowReturnOffice() {
        this.booksInBorrowReturnOffice = new ArrayList<>();
    }

    public void addBook(LibraryBookId bookId) {
        booksInBorrowReturnOffice.add(bookId);
    }

    public ArrayList<LibraryBookId> getBooks() {
        return booksInBorrowReturnOffice;
    }

    public void clearBooks() {
        booksInBorrowReturnOffice.clear();
    }
}
