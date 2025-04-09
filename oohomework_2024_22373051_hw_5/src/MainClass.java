import com.oocourse.elevator1.TimableOutput;

import java.util.ArrayList;

public class MainClass {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        ArrayList<RequestTable> requestTables = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            RequestTable requestTable = new RequestTable();
            Elevator elevator = new Elevator(i + 1, requestTable);
            requestTables.add(requestTable);
            elevator.start();
        }
        InputThread inputThread = new InputThread(requestTables);
        inputThread.start();
    }
}
