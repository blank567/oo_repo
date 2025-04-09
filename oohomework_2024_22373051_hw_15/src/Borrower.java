import com.oocourse.library3.LibraryBookId;
import com.oocourse.library3.annotation.SendMessage;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;

public class Borrower {
    private String id;
    private HashMap<LibraryBookId, LocalDate> borrowedBooks;
    private boolean isContainB;
    private boolean isContainBU;
    private int creditScore;
    private HashSet<LibraryBookId> books;

    public Borrower(String id) {
        this.id = id;
        this.borrowedBooks = new HashMap<>();
        this.isContainB = false;
        creditScore = 10;
        this.books = new HashSet<>();
    }

    @SendMessage(from = "Borrower", to = "Library")
    public void getOrderedBook() {
    }

    public void check(LocalDate data) {
        for (LibraryBookId bookId : borrowedBooks.keySet()) {
            if (data.isAfter(borrowedBooks.get(bookId)) && books.contains(bookId)) {
                addCreditScore(-2);
                books.remove(bookId);
            }
        }
    }

    public void addCreditScore(int num) {
        this.creditScore += num;
        this.creditScore = Math.min(this.creditScore, 20);
    }

    public int getCreditScore() {
        return this.creditScore;
    }

    public boolean isHaveEnoughCreditScore() {
        return this.creditScore >= 0;
    }

    public void addBook(LibraryBookId bookId, LocalDate date) {
        if (bookId.isTypeB()) {
            isContainB = true;
        }
        if (bookId.isTypeBU()) {
            isContainBU = true;
        }
        borrowedBooks.put(bookId, date);
        books.add(bookId);
    }

    public void removeBook(LibraryBookId bookId) {
        if (bookId.isTypeB()) {
            isContainB = false;
        }
        if (bookId.isTypeBU()) {
            isContainBU = false;
        }
        borrowedBooks.remove(bookId);
        books.remove(bookId);
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
