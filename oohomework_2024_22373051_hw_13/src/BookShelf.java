import com.oocourse.library1.LibraryBookId;

import java.util.HashMap;

public class BookShelf {
    private HashMap<LibraryBookId, Integer> books; //书架上的书

    public BookShelf(HashMap<LibraryBookId, Integer> books) {
        this.books = books;
    }

    public void addBook(LibraryBookId bookId) {
        books.put(bookId, books.get(bookId) + 1);
    }

    public boolean isContainBook(LibraryBookId bookId) {
        return books.containsKey(bookId);
    }

    public void removeBook(LibraryBookId bookId) {
        books.put(bookId, books.get(bookId) - 1);
    }

    public int getBookNum(LibraryBookId bookId) {
        if (!books.containsKey(bookId)) {
            return 0;
        }
        return books.get(bookId);
    }
}
