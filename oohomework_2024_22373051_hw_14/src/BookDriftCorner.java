import com.oocourse.library2.LibraryBookId;

import java.util.HashMap;

public class BookDriftCorner {
    private HashMap<LibraryBookId, Integer> booksInCorner;
    private HashMap<LibraryBookId, Integer> timesOfBooks;

    public BookDriftCorner() {
        this.booksInCorner = new HashMap<>();
        this.timesOfBooks = new HashMap<>();
    }

    public void addBook(LibraryBookId bookId) {
        if (booksInCorner.containsKey(bookId)) {
            booksInCorner.put(bookId, booksInCorner.get(bookId) + 1);
        } else {
            booksInCorner.put(bookId, 1);
            timesOfBooks.put(bookId, 0);
        }
    }

    public void addBorrowTimes(LibraryBookId bookId) {
        if (timesOfBooks.containsKey(bookId)) {
            timesOfBooks.put(bookId, timesOfBooks.get(bookId) + 1);
        }
    }

    public void removeBook(LibraryBookId bookId) {
        if (booksInCorner.containsKey(bookId)) {
            booksInCorner.put(bookId, booksInCorner.get(bookId) - 1);
        }
    }

    public void clearBook(LibraryBookId bookId) {
        if (booksInCorner.containsKey(bookId)) {
            booksInCorner.remove(bookId);
        }
    }

    public int getBookNum(LibraryBookId bookId) {
        if (!booksInCorner.containsKey(bookId)) {
            return 0;
        }
        return booksInCorner.get(bookId);
    }

    public boolean isContainBook(LibraryBookId bookId) {
        return booksInCorner.containsKey(bookId);
    }

    public int getTimesOfBook(LibraryBookId bookId) {
        if (!timesOfBooks.containsKey(bookId)) {
            return 0;
        }
        return timesOfBooks.get(bookId);
    }
}
