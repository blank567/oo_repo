import com.oocourse.library2.LibraryCommand;

import java.util.ArrayList;

public class Appointment {
    private ArrayList<LibraryCommand> orders;

    public Appointment() {
        orders = new ArrayList<>();
    }

    public void addOrder(LibraryCommand order) {
        orders.add(order);
    }

    public ArrayList<LibraryCommand> getOrders() {
        return orders;
    }
}
