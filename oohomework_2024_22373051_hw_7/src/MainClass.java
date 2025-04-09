import com.oocourse.elevator3.TimableOutput;

import java.util.ArrayList;

public class MainClass {
    public static void main(String[] args) {
        TimableOutput.initStartTimestamp();
        RequestTable allRequestTables = new RequestTable();
        ArrayList<RequestTable> requestTables = new ArrayList<>();
        ArrayList<RequestTable> newRequestTables = new ArrayList<>();
        ResetTable resetRequests = new ResetTable();
        ArrayList<Elevator> elevators = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            RequestTable requestTable = new RequestTable();
            RequestTable newRequestTable = new RequestTable();
            Elevator elevator = new Elevator(i + 1, requestTable, allRequestTables,
                    resetRequests, newRequestTable);
            elevators.add(elevator);
            requestTables.add(requestTable);
            newRequestTables.add(newRequestTable);
            elevator.start();
        }
        Schedule schedule = new Schedule(allRequestTables, requestTables, elevators,
                resetRequests, newRequestTables);
        schedule.start();

        InputThread inputThread = new InputThread(allRequestTables, resetRequests,
                requestTables, newRequestTables);
        inputThread.start();
    }
}
