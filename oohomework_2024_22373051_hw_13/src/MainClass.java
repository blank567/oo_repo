import com.oocourse.library1.LibraryBookId;
import com.oocourse.library1.LibraryCommand;
import com.oocourse.library1.LibraryRequest;

import java.time.LocalDate;
import java.util.HashMap;

import static com.oocourse.library1.LibrarySystem.SCANNER;

public class MainClass {
    public static void main(String[] args) {
        HashMap<LibraryBookId, Integer> books =
                (HashMap<LibraryBookId, Integer>) SCANNER.getInventory();
        Library library = new Library(books);
        while (true) {
            LibraryCommand<?> command = SCANNER.nextCommand();
            if (command == null) {
                break;
            }
            LocalDate data = command.getDate();
            if (command.getCmd().equals("OPEN")) {
                library.open(data);
            } else if (command.getCmd().equals("CLOSE")) {
                library.close(data);
            } else {
                LibraryRequest request = (LibraryRequest) command.getCmd();
                String studentId = request.getStudentId();
                LibraryBookId bookId = request.getBookId();
                switch (request.getType()) {
                    case BORROWED:
                        library.borrowBook(studentId, bookId, data, request);
                        break;
                    case RETURNED:
                        library.returnBook(studentId, bookId, data, request);
                        break;
                    case ORDERED:
                        library.orderBook(studentId, bookId, data, request, command);
                        break;
                    case PICKED:
                        library.pickBook(studentId, bookId, data, request);
                        break;
                    case QUERIED:
                        library.queryBook(data, bookId);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
