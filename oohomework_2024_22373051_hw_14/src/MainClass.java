import com.oocourse.library2.LibraryBookId;
import com.oocourse.library2.LibraryCloseCmd;
import com.oocourse.library2.LibraryCommand;
import com.oocourse.library2.LibraryOpenCmd;
import com.oocourse.library2.LibraryReqCmd;
import com.oocourse.library2.LibraryRequest;

import java.time.LocalDate;
import java.util.HashMap;

import static com.oocourse.library2.LibrarySystem.SCANNER;

public class MainClass {
    public static void main(String[] args) {
        HashMap<LibraryBookId, Integer> books =
                (HashMap<LibraryBookId, Integer>) SCANNER.getInventory();
        Library library = new Library(books);
        while (true) {
            LibraryCommand command = SCANNER.nextCommand();
            if (command == null) {
                break;
            }
            LocalDate data = command.getDate();
            if (command instanceof LibraryOpenCmd) {
                library.open(data);
            } else if (command instanceof LibraryCloseCmd) {
                library.close(data);
            } else {
                LibraryRequest request = ((LibraryReqCmd) command).getRequest();
                LibraryBookId bookId = request.getBookId();
                switch (request.getType()) {
                    case BORROWED:
                        library.borrowBook(command, request);
                        break;
                    case RETURNED:
                        library.returnBook(command, request);
                        break;
                    case ORDERED:
                        library.orderBook(command, request);
                        break;
                    case PICKED:
                        library.pickBook(command, request);
                        break;
                    case QUERIED:
                        library.queryBook(data, bookId);
                        break;
                    case DONATED:
                        library.donateBook(command, request);
                        break;
                    case RENEWED:
                        library.renewBook(command, request);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
