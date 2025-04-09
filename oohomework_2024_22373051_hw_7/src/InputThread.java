import com.oocourse.elevator3.DoubleCarResetRequest;
import com.oocourse.elevator3.ElevatorInput;
import com.oocourse.elevator3.NormalResetRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.Request;

import java.io.IOException;
import java.util.ArrayList;

public class InputThread extends Thread {
    private RequestTable allRequestTables;
    private ResetTable resetRequests;
    private ArrayList<RequestTable> requestTables;
    private ArrayList<RequestTable> newRequestTables;

    public InputThread(RequestTable allRequestTables, ResetTable resetRequests,
                       ArrayList<RequestTable> requestTables,
                       ArrayList<RequestTable> newRequestTables) {
        this.allRequestTables = allRequestTables;
        this.resetRequests = resetRequests;
        this.requestTables = requestTables;
        this.newRequestTables = newRequestTables;
    }

    @Override
    public void run() {
        ElevatorInput elevatorInput = new ElevatorInput(System.in);
        while (true) {
            Request request = elevatorInput.nextRequest();
            if (request == null) {
                allRequestTables.setEnd(true);
                break;
            } else if (request instanceof PersonRequest) {
                allRequestTables.addPersonRequest((PersonRequest) request);
            } else if (request instanceof NormalResetRequest) {
                resetRequests.addNormalResetRequest((NormalResetRequest) request);
                for (int i = 0; i < 6; i++) {
                    requestTables.get(i).just();
                }
                for (int i = 0; i < 6; i++) {
                    newRequestTables.get(i).just();
                }
            } else if (request instanceof DoubleCarResetRequest) {
                resetRequests.addDoubleCarResetRequest((DoubleCarResetRequest) request);
                for (int i = 0; i < 6; i++) {
                    requestTables.get(i).just();
                }
                for (int i = 0; i < 6; i++) {
                    newRequestTables.get(i).just();
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
