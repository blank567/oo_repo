import com.oocourse.library2.LibraryBookId;
import com.oocourse.library2.LibraryCommand;
import com.oocourse.library2.LibraryMoveInfo;
import com.oocourse.library2.LibraryReqCmd;
import com.oocourse.library2.LibraryRequest;
import com.oocourse.library2.LibrarySystem;
import com.oocourse.library2.annotation.Trigger;

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
    private final BookDriftCorner bookDriftCorner;

    public Library(HashMap<LibraryBookId, Integer> books) {
        this.moveInfos = new ArrayList<>();
        this.bookShelf = new BookShelf(books);
        this.borrowReturnOffice = new BorrowReturnOffice();
        this.appointment = new Appointment();
        this.borrowers = new HashMap<>();
        this.orders = new ArrayList<>();
        this.bookDriftCorner = new BookDriftCorner();
    }

    @Trigger(from = "BookShelf", to = { "BorrowReturnOffice", "Borrower" })
    @Trigger(from = "BookDriftCorner", to = "Borrower")
    public void borrowBook(LibraryCommand libraryCommand, LibraryRequest libraryRequest) {
        String id = libraryRequest.getStudentId();
        LibraryBookId bookId = libraryRequest.getBookId();
        if (!borrowers.containsKey(id)) {
            borrowers.put(id, new Borrower(id));
        }
        Borrower borrower = borrowers.get(id);
        if (bookId.isTypeA() || bookId.isTypeAU()) {
            LibrarySystem.PRINTER.reject(libraryCommand);
            return;
        }
        if (bookShelf.getBookNum(bookId) > 0 || bookDriftCorner.getBookNum(bookId) > 0) {
            if (bookId.isTypeB()) {
                if (borrower.isContainB()) {
                    borrowReturnOffice.addBook(bookId);
                    LibrarySystem.PRINTER.reject(libraryCommand);
                } else {
                    borrower.addBook(bookId, libraryCommand.getDate().plusDays(30));
                    LibrarySystem.PRINTER.accept(libraryCommand);
                }
            } else if (bookId.isTypeBU()) {
                if (borrower.isContainBU()) {
                    borrowReturnOffice.addBook(bookId);
                    LibrarySystem.PRINTER.reject(libraryCommand);
                } else {
                    borrower.addBook(bookId, libraryCommand.getDate().plusDays(7));
                    LibrarySystem.PRINTER.accept(libraryCommand);
                }
            } else if (bookId.isTypeC()) {
                if (borrower.isContainBook(bookId)) {
                    borrowReturnOffice.addBook(bookId);
                    LibrarySystem.PRINTER.reject(libraryCommand);
                } else {
                    borrower.addBook(bookId, libraryCommand.getDate().plusDays(60));
                    LibrarySystem.PRINTER.accept(libraryCommand);
                }
            } else if (bookId.isTypeCU()) {
                if (borrower.isContainBook(bookId)) {
                    borrowReturnOffice.addBook(bookId);
                    LibrarySystem.PRINTER.reject(libraryCommand);
                } else {
                    borrower.addBook(bookId, libraryCommand.getDate().plusDays(14));
                    LibrarySystem.PRINTER.accept(libraryCommand);
                }
            }
            bookShelf.removeBook(bookId);
            bookDriftCorner.removeBook(bookId);
        } else {
            LibrarySystem.PRINTER.reject(libraryCommand);
        }
    }

    @Trigger(from = "Borrower", to = "BorrowReturnOffice")
    public void returnBook(LibraryCommand libraryCommand, LibraryRequest libraryRequest) {
        String id = libraryRequest.getStudentId();
        LibraryBookId bookId = libraryRequest.getBookId();
        LocalDate data = libraryCommand.getDate();
        Borrower borrower = borrowers.get(id);
        if (bookId.isTypeBU() || bookId.isTypeCU()) {
            bookDriftCorner.addBorrowTimes(bookId);
        }
        long borrowDate = ChronoUnit.DAYS.between(data, borrower.getReturnDate(bookId));
        borrower.removeBook(bookId);
        borrowReturnOffice.addBook(bookId);
        if (borrowDate >= 0) {
            LibrarySystem.PRINTER.accept(libraryCommand, "not overdue");
        } else {
            LibrarySystem.PRINTER.accept(libraryCommand, "overdue");
        }
    }

    @Trigger(from = "BookShelf", to = "Appointment")
    public void orderBook(LibraryCommand libraryCommand, LibraryRequest libraryRequest) {
        String id = libraryRequest.getStudentId();
        LibraryBookId bookId = libraryRequest.getBookId();
        if (!borrowers.containsKey(id)) {
            borrowers.put(id, new Borrower(id));
        }
        Borrower borrower = borrowers.get(id);
        if (bookId.isTypeA()) {
            LibrarySystem.PRINTER.reject(libraryCommand);
        } else if (bookId.isTypeB()) {
            if (borrower.isContainB()) {
                LibrarySystem.PRINTER.reject(libraryCommand);
            } else {
                orders.add(libraryCommand);
                LibrarySystem.PRINTER.accept(libraryCommand);
            }
        } else if (bookId.isTypeC()) {
            if (borrower.isContainBook(bookId)) {
                LibrarySystem.PRINTER.reject(libraryCommand);
            } else {
                orders.add(libraryCommand);
                LibrarySystem.PRINTER.accept(libraryCommand);
            }
        } else {
            LibrarySystem.PRINTER.reject(libraryCommand);
        }
    }

    @Trigger(from = "BorrowReturnOffice", to = "Borrower")
    public void pickBook(LibraryCommand libraryCommand, LibraryRequest libraryRequest) {
        String id = libraryRequest.getStudentId();
        LibraryBookId bookId = libraryRequest.getBookId();
        if (!borrowers.containsKey(id)) {
            borrowers.put(id, new Borrower(id));
        }
        Borrower borrower = borrowers.get(id);
        for (LibraryCommand command : appointment.getOrders()) {
            LibraryRequest request = ((LibraryReqCmd) command).getRequest();
            if (request.getStudentId().equals(id) && request.getBookId().equals(bookId)) {
                if (bookId.isTypeB()) {
                    if (borrower.isContainB()) {
                        LibrarySystem.PRINTER.reject(libraryCommand);
                    } else {
                        appointment.getOrders().remove(command);
                        borrower.addBook(bookId, libraryCommand.getDate().plusDays(30));
                        LibrarySystem.PRINTER.accept(libraryCommand);
                    }
                } else if (bookId.isTypeC()) {
                    if (borrower.isContainBook(bookId)) {
                        LibrarySystem.PRINTER.reject(libraryCommand);
                    } else {
                        appointment.getOrders().remove(command);
                        borrower.addBook(bookId, libraryCommand.getDate().plusDays(60));
                        LibrarySystem.PRINTER.accept(libraryCommand);
                    }
                }
                return;
            }
        }
        LibrarySystem.PRINTER.reject(libraryCommand);
    }

    @Trigger(from = "Appointment", to = "BookShelf")
    public void open(LocalDate data) {
        Iterator<LibraryCommand> iterator = appointment.getOrders().iterator();
        while (iterator.hasNext()) {
            LibraryCommand command = iterator.next();
            if (ChronoUnit.DAYS.between(command.getDate(), data) >= 5) {
                LibraryRequest request = ((LibraryReqCmd) command).getRequest();
                LibraryBookId bookId = request.getBookId();
                bookShelf.addBook(bookId);
                iterator.remove();
                LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(bookId, "ao", "bs");
                moveInfos.add(libraryMoveInfo);
            }
        }
        for (ListIterator<LibraryCommand> it = orders.listIterator(); it.hasNext(); ) {
            LibraryCommand command = it.next();
            LibraryRequest request = ((LibraryReqCmd) command).getRequest();
            if (bookShelf.getBookNum(request.getBookId()) > 0) {
                LibraryCommand newCommand = new LibraryReqCmd(data, request);
                appointment.addOrder(newCommand);
                LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(request.getBookId(),
                        "bs", "ao", request.getStudentId());
                bookShelf.removeBook(request.getBookId());
                moveInfos.add(libraryMoveInfo);
                it.remove();
            }
        }
        LibrarySystem.PRINTER.move(data, moveInfos);
        moveInfos.clear();
    }

    @Trigger(from = "BorrowReturnOffice", to = { "BookShelf", "BookDriftCorner" })
    public void close(LocalDate data) {
        for (LibraryBookId bookId : borrowReturnOffice.getBooks()) {
            if (bookId.isTypeBU() || bookId.isTypeCU()) {
                if (bookDriftCorner.getTimesOfBook(bookId) >= 2) {
                    LibraryBookId bookId1 = new LibraryBookId(bookId.isTypeBU() ?
                            LibraryBookId.Type.B : LibraryBookId.Type.C, bookId.getUid());
                    bookShelf.addBooks(bookId1, bookDriftCorner.getBookNum(bookId) + 1);
                    bookDriftCorner.clearBook(bookId);
                    LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(bookId, "bro", "bs");
                    moveInfos.add(libraryMoveInfo);
                } else {
                    bookDriftCorner.addBook(bookId);
                    LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(bookId, "bro", "bdc");
                    moveInfos.add(libraryMoveInfo);
                }
            } else {
                bookShelf.addBook(bookId);
                LibraryMoveInfo libraryMoveInfo = new LibraryMoveInfo(bookId, "bro", "bs");
                moveInfos.add(libraryMoveInfo);
            }
        }
        borrowReturnOffice.clearBooks();
        LibrarySystem.PRINTER.move(data, moveInfos);
        moveInfos.clear();
    }

    public void queryBook(LocalDate data, LibraryBookId bookId) {
        if (bookShelf.isContainBook(bookId)) {
            LibrarySystem.PRINTER.info(data, bookId, bookShelf.getBookNum(bookId));
        } else if (bookDriftCorner.isContainBook(bookId)) {
            LibrarySystem.PRINTER.info(data, bookId, bookDriftCorner.getBookNum(bookId));
        }
    }

    @Trigger(from = "Borrower", to = "BookDriftCorner")
    public void donateBook(LibraryCommand libraryCommand, LibraryRequest libraryRequest) {
        bookDriftCorner.addBook(libraryRequest.getBookId());
        LibrarySystem.PRINTER.accept(libraryCommand);
    }

    public boolean isHaveOrder(LibraryBookId libraryBookId) {
        for (LibraryCommand command : orders) {
            LibraryRequest request = ((LibraryReqCmd) command).getRequest();
            if (request.getBookId().equals(libraryBookId)) {
                return true;
            }
        }
        for (LibraryCommand command : appointment.getOrders()) {
            LibraryRequest request = ((LibraryReqCmd) command).getRequest();
            if (request.getBookId().equals(libraryBookId)) {
                return true;
            }
        }
        return false;
    }

    public void renewBook(LibraryCommand libraryCommand, LibraryRequest libraryRequest) {
        LocalDate data = libraryCommand.getDate();
        LibraryBookId bookId = libraryRequest.getBookId();
        String id = libraryRequest.getStudentId();
        Borrower borrower = borrowers.get(id);
        if (bookId.isTypeBU() || bookId.isTypeCU()) {
            LibrarySystem.PRINTER.reject(libraryCommand);
            return;
        }
        long days = ChronoUnit.DAYS.between(data, borrower.getReturnDate(bookId));
        if (!(days >= 0 && days < 5)) {
            LibrarySystem.PRINTER.reject(libraryCommand);
        } else {
            if (bookShelf.getBookNum(bookId) == 0 && isHaveOrder(bookId)) {
                LibrarySystem.PRINTER.reject(libraryCommand);
            } else {
                borrower.setNewReturnData(bookId);
                LibrarySystem.PRINTER.accept(libraryCommand);
            }
        }
    }
}
