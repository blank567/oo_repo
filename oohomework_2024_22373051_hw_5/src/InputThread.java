import com.oocourse.elevator1.ElevatorInput;
import com.oocourse.elevator1.PersonRequest;

import java.io.IOException;
import java.util.ArrayList;

public class InputThread extends Thread {
    private ArrayList<RequestTable> requestTables;

    public InputThread(ArrayList<RequestTable> requestTables) {
        this.requestTables = requestTables;
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            PersonRequest personRequest = elevatorInput.nextPersonRequest();
            if (personRequest == null) {
                for (RequestTable requestTable : requestTables) {
                    requestTable.setEnd(true);
                }
                break;
            } else {
                requestTables.get(personRequest.getElevatorId() - 1).
                        addPersonRequest(personRequest);
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
