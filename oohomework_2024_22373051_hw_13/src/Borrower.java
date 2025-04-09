import com.oocourse.library1.LibraryBookId;

import java.util.HashSet;

public class Borrower {
    private String id;
    private HashSet<LibraryBookId> borrowedBooks;
    private boolean isContainB;

    public Borrower(String id) {
        this.id = id;
        this.borrowedBooks = new HashSet<>();
        this.isContainB = false;
    }

    public void addBook(LibraryBookId bookId) {
        if (bookId.isTypeB()) {
            isContainB = true;
        }
        borrowedBooks.add(bookId);
    }

    public void removeBook(LibraryBookId bookId) {
        if (bookId.isTypeB()) {
            isContainB = false;
        }
        borrowedBooks.remove(bookId);
    }

    public boolean isContainB() {
        return isContainB;
    }

    public boolean isContainBook(LibraryBookId bookId) {
        return borrowedBooks.contains(bookId);
    }
}
