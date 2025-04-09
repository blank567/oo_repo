import com.oocourse.library2.LibraryBookId;

import java.time.LocalDate;
import java.util.HashMap;

public class Borrower {
    private String id;
    private HashMap<LibraryBookId, LocalDate> borrowedBooks;
    private boolean isContainB;
    private boolean isContainBU;

    public Borrower(String id) {
        this.id = id;
        this.borrowedBooks = new HashMap<>();
        this.isContainB = false;
    }

    public void addBook(LibraryBookId bookId, LocalDate date) {
        if (bookId.isTypeB()) {
            isContainB = true;
        }
        if (bookId.isTypeBU()) {
            isContainBU = true;
        }
        borrowedBooks.put(bookId, date);
    }

    public void removeBook(LibraryBookId bookId) {
        if (bookId.isTypeB()) {
            isContainB = false;
        }
        if (bookId.isTypeBU()) {
            isContainBU = false;
        }
        borrowedBooks.remove(bookId);
    }

    public boolean isContainB() {
        return isContainB;
    }

    public boolean isContainBU() {
        return isContainBU;
    }

    public boolean isContainBook(LibraryBookId bookId) {
        return borrowedBooks.containsKey(bookId);
    }

    public LocalDate getReturnDate(LibraryBookId bookId) {
        return borrowedBooks.get(bookId);
    }

    public void setNewReturnData(LibraryBookId bookId) {
        LocalDate date = borrowedBooks.get(bookId);
        LocalDate newDate = date.plusDays(30);
        borrowedBooks.put(bookId, newDate);
    }
}
