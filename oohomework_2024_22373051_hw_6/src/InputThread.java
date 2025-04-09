import com.oocourse.elevator2.ElevatorInput;
import com.oocourse.elevator2.PersonRequest;
import com.oocourse.elevator2.Request;
import com.oocourse.elevator2.ResetRequest;

import java.io.IOException;
import java.util.ArrayList;

public class InputThread extends Thread {
    private RequestTable allRequestTables;
    private ResetTable resetRequests;
    private ArrayList<RequestTable> requestTables;

    public InputThread(RequestTable allRequestTables, ResetTable resetRequests,
                       ArrayList<RequestTable> requestTables) {
        this.allRequestTables = allRequestTables;
        this.resetRequests = resetRequests;
        this.requestTables = requestTables;
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            Request request = elevatorInput.nextRequest();
            //PersonRequest personRequest = (PersonRequest) elevatorInput.nextRequest();
            if (request == null) {
                allRequestTables.setEnd(true);
                break;
            } else if (request instanceof PersonRequest) {
                allRequestTables.addPersonRequest((PersonRequest) request);
            } else if (request instanceof ResetRequest) {
                resetRequests.addResetRequest((ResetRequest) request);
                for (int i = 0; i < 6; i++) {
                    requestTables.get(i).just();
                }
            }
        }
        try {
            elevatorInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
