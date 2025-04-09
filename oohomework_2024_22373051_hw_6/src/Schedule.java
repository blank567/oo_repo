import com.oocourse.elevator2.PersonRequest;

import java.util.ArrayList;
import java.util.Random;

public class Schedule extends Thread {
    private RequestTable allRequestTables;
    private ArrayList<RequestTable> requestTables;
    private ResetTable resetRequests;
    private ArrayList<Elevator> elevators;

    public Schedule(RequestTable allRequestTables, ArrayList<RequestTable> requestTables,
                    ArrayList<Elevator> elevators, ResetTable resetRequests) {
        this.allRequestTables = allRequestTables;
        this.requestTables = requestTables;
        this.resetRequests = resetRequests;
        this.elevators = elevators;
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
            int fromFloor = personRequest.getFromFloor();
            int toFloor = personRequest.getToFloor();
            int diatance = 100;
            int minDistance = 100;
            Random random = new Random();
            int cnt = random.nextInt(6);
            for (int i = 0; i < 6; i++) {
                try {
                    minDistance = elevators.get(i).getWhich(fromFloor, toFloor);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (minDistance < diatance) {
                    diatance = minDistance;
                    cnt = i;
                }
            }
            requestTables.get(cnt).addPersonRequest(personRequest, cnt + 1);
        }
    }
}
