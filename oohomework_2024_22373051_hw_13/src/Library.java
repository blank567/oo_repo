import com.oocourse.library1.LibraryBookId;
import com.oocourse.library1.LibraryCommand;
import com.oocourse.library1.LibraryMoveInfo;
import com.oocourse.library1.LibraryRequest;
import com.oocourse.library1.LibrarySystem;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class Library {
    private final BookShelf bookShelf;
    private final ArrayList<LibraryMoveInfo> moveInfos;  //移动的书
    private final BorrowReturnOffice borrowReturnOffice;
    private final Appointment appointment;
    private final HashMap<String, Borrower> borrowers;
    private final ArrayList<LibraryCommand> orders;

    public Library(HashMap<LibraryBookId, Integer> books) {
        this.moveInfos = new ArrayList<>();
        this.bookShelf = new BookShelf(books);
        this.borrowReturnOffice = new BorrowReturnOffice();
        this.appointment = new Appointment();
        borrowers = new HashMap<>();
        orders = new ArrayList<>();
    }

    public void borrowBook(String id, LibraryBookId bookId,
                           LocalDate data, LibraryRequest libraryRequest) {
        if (!borrowers.containsKey(id)) {
            borrowers.put(id, new Borrower(id));
        }
        Borrower borrower = borrowers.get(id);
        if (bookId.isTypeA()) {
            LibrarySystem.PRINTER.reject(data, libraryRequest);
            return;
        }
        if (bookShelf.isContainBook(bookId) && bookShelf.getBookNum(bookId) > 0) {
            if (bookId.isTypeB()) {
                if (borrower.isContainB()) {
                    borrowReturnOffice.addBook(bookId);
                    LibrarySystem.PRINTER.reject(data, libraryRequest);
                } else {
                    borrower.addBook(bookId);
                    LibrarySystem.PRINTER.accept(data, libraryRequest);
                }
                bookShelf.removeBook(bookId);
            } else if (bookId.isTypeC()) {
                if (borrower.isContainBook(bookId)) {
                    borrowReturnOffice.addBook(bookId);
                    LibrarySystem.PRINTER.reject(data, libraryRequest);
                } else {
                    borrower.addBook(bookId);
                    LibrarySystem.PRINTER.accept(data, libraryRequest);
                }
                bookShelf.removeBook(bookId);
            }
        } else {
            LibrarySystem.PRINTER.reject(data, libraryRequest);
        }
    }

    public void returnBook(String id, LibraryBookId bookId,
                           LocalDate data, LibraryRequest libraryRequest) {
        Borrower borrower = borrowers.get(id);
        borrower.removeBook(bookId);
        borrowReturnOffice.addBook(bookId);
        LibrarySystem.PRINTER.accept(data, libraryRequest);
    }

    public void orderBook(String id, LibraryBookId bookId, LocalDate data,
                          LibraryRequest libraryRequest, LibraryCommand command) {
        if (!borrowers.containsKey(id)) {
            borrowers.put(id, new Borrower(id));
        }
        Borrower borrower = borrowers.get(id);
        if (bookId.isTypeA()) {
            LibrarySystem.PRINTER.reject(data, libraryRequest);
        } else if (bookId.isTypeB()) {
            if (borrower.isContainB()) {
                LibrarySystem.PRINTER.reject(data, libraryRequest);
            } else {
                orders.add(command);
                LibrarySystem.PRINTER.accept(data, libraryRequest);
            }
        } else if (bookId.isTypeC()) {
            if (borrower.isContainBook(bookId)) {
                LibrarySystem.PRINTER.reject(data, libraryRequest);
            } else {
                orders.add(command);
                LibrarySystem.PRINTER.accept(data, libraryRequest);
            }
        }
    }

    public void pickBook(String id, LibraryBookId bookId,
                         LocalDate data, LibraryRequest libraryRequest) {
        if (!borrowers.containsKey(id)) {
            borrowers.put(id, new Borrower(id));
        }
        Borrower borrower = borrowers.get(id);
        for (LibraryCommand command : appointment.getOrders()) {
            LibraryRequest request = (LibraryRequest) command.getCmd();
            if (request.getStudentId().equals(id) && request.getBookId().equals(bookId)) {
                if (bookId.isTypeB()) {
                    if (borrower.isContainB()) {
                        LibrarySystem.PRINTER.reject(data, libraryRequest);
                    } else {
                        appointment.getOrders().remove(command);
                        borrower.addBook(bookId);
                        LibrarySystem.PRINTER.accept(data, libraryRequest);
                    }
                } else if (bookId.isTypeC()) {
                    if (borrower.isContainBook(bookId)) {
                        LibrarySystem.PRINTER.reject(data, libraryRequest);
                    } else {
                        appointment.getOrders().remove(command);
                        borrower.addBook(bookId);
                        LibrarySystem.PRINTER.accept(data, libraryRequest);
                    }
                }
                return;
            }
        }
        LibrarySystem.PRINTER.reject(data, libraryRequest);
    }

    public void open(LocalDate data) {
        Iterator<LibraryCommand> iterator = appointment.getOrders().iterator();
        while (iterator.hasNext()) {
            LibraryCommand command = iterator.next();
            if (ChronoUnit.DAYS.between(command.getDate(), data) >= 5) {
                LibraryRequest request = (LibraryRequest) command.getCmd();
                LibraryBookId bookId = request.getBookId();
                bookShelf.addBook(bookId);
                iterator.remove();
                LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(bookId, "ao", "bs");
                moveInfos.add(libraryMoveInfo);
            }
        }
        for (ListIterator<LibraryCommand> iterator1 = orders.listIterator(); iterator1.hasNext();) {
            LibraryCommand command = iterator1.next();
            LibraryRequest request = (LibraryRequest) command.getCmd();
            if (bookShelf.getBookNum(request.getBookId()) > 0) {
                LibraryCommand newCommand = new LibraryCommand(data, request);
                appointment.addOrder(newCommand);
                LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(request.getBookId(),
                        "bs", "ao", request.getStudentId());
                bookShelf.removeBook(request.getBookId());
                moveInfos.add(libraryMoveInfo);
                iterator1.remove();
            }
        }
        LibrarySystem.PRINTER.move(data, moveInfos);
        moveInfos.clear();
    }

    public void close(LocalDate data) {
        for (LibraryBookId bookId : borrowReturnOffice.getBooks()) {
            bookShelf.addBook(bookId);
            LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(bookId, "bro", "bs");
            moveInfos.add(libraryMoveInfo);
        }
        borrowReturnOffice.clearBooks();
        LibrarySystem.PRINTER.move(data, moveInfos);
        moveInfos.clear();
    }

    public void queryBook(LocalDate data, LibraryBookId bookId) {
        LibrarySystem.PRINTER.info(data, bookId, bookShelf.getBookNum(bookId));
    }
}
