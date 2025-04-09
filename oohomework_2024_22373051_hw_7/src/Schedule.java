import com.oocourse.elevator3.PersonRequest;

import java.util.ArrayList;
import java.util.Random;

public class Schedule extends Thread {
    private RequestTable allRequestTables;
    private ArrayList<RequestTable> requestTables;
    private ResetTable resetRequests;
    private ArrayList<Elevator> elevators;
    private ArrayList<RequestTable> newRequestTables;

    public Schedule(RequestTable allRequestTables, ArrayList<RequestTable> requestTables,
                    ArrayList<Elevator> elevators, ResetTable resetRequests,
                    ArrayList<RequestTable> newRequestTables) {
        this.allRequestTables = allRequestTables;
        this.requestTables = requestTables;
        this.resetRequests = resetRequests;
        this.elevators = elevators;
        this.newRequestTables = newRequestTables;
    }

    @Override
    public void run() {
        while (true) {
            if (allRequestTables.isEnd() && resetRequests.isEmpty() && allRequestTables.isEmpty()) {
                for (int i = 0; i < 6; i++) {
                    requestTables.get(i).setEnd(true);
                }
                return;
            }
            PersonRequest personRequest = allRequestTables.getOneRequestAndRemove();
            if (personRequest == null) {
                continue;
            }
            Random random = new Random();
            int cnt = random.nextInt(6);
            requestTables.get(cnt).addPersonRequest(personRequest, cnt + 1, 0,
                    requestTables.get(cnt).getIsDouble(), requestTables.get(cnt).getMaxFloor());
            newRequestTables.get(cnt).addPersonRequest(personRequest, cnt + 1, 1,
                    requestTables.get(cnt).getIsDouble(), requestTables.get(cnt).getMaxFloor());
        }
    }
}
