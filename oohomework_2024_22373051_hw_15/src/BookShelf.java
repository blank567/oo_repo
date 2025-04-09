import com.oocourse.library3.LibraryBookId;

import java.util.HashMap;

public class BookShelf {
    private HashMap<LibraryBookId, Integer> books; //书架上的书

    public BookShelf(HashMap<LibraryBookId, Integer> books) {
        this.books = books;
    }

    public void addBook(LibraryBookId bookId) {
        books.put(bookId, books.get(bookId) + 1);
    }

    public void addBooks(LibraryBookId bookId, int num) {
        books.put(bookId, num);
    }

    public boolean isContainBook(LibraryBookId bookId) {
        return books.containsKey(bookId);
    }

    public void removeBook(LibraryBookId bookId) {
        if (books.containsKey(bookId)) {
            books.put(bookId, books.get(bookId) - 1);
        }
    }

    public int getBookNum(LibraryBookId bookId) {
        if (!books.containsKey(bookId)) {
            return 0;
        }
        return books.get(bookId);
    }
}
