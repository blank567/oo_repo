import com.oocourse.elevator2.TimableOutput;

import java.util.ArrayList;

public class MainClass {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        RequestTable allRequestTables = new RequestTable();
        ArrayList<RequestTable> requestTables = new ArrayList<>();
        ResetTable resetRequests = new ResetTable();
        ArrayList<Elevator> elevators = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            RequestTable requestTable = new RequestTable();
            Elevator elevator = new Elevator(i + 1, requestTable, allRequestTables, resetRequests);
            elevators.add(elevator);
            requestTables.add(requestTable);
            elevator.start();
        }
        Schedule schedule = new Schedule(allRequestTables, requestTables, elevators, resetRequests);
        schedule.start();

        InputThread inputThread = new InputThread(allRequestTables, resetRequests, requestTables);
        inputThread.start();
    }
}
