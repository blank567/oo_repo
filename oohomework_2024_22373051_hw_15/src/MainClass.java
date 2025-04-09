import com.oocourse.library3.LibraryBookId;
import com.oocourse.library3.LibraryCloseCmd;
import com.oocourse.library3.LibraryCommand;
import com.oocourse.library3.LibraryOpenCmd;
import com.oocourse.library3.LibraryQcsCmd;
import com.oocourse.library3.LibraryReqCmd;
import com.oocourse.library3.LibraryRequest;

import java.time.LocalDate;
import java.util.HashMap;

import static com.oocourse.library3.LibrarySystem.SCANNER;

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
            } else if (command instanceof LibraryQcsCmd) {
                library.queryCreditScore(data, ((LibraryQcsCmd) command).getStudentId());
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
