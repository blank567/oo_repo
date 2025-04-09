import com.oocourse.elevator1.PersonRequest;
import com.oocourse.elevator1.TimableOutput;

import java.util.HashSet;

public class Elevator extends Thread {
    private final int id;//电梯id
    private final RequestTable requestTable;
    private int floor;
    private final HashSet<PersonRequest> personInElevator;
    private final int capacity;
    private boolean up;
    private final int waitTime;

    public Elevator(int id, RequestTable requestTable) {
        this.id = id;
        this.requestTable = requestTable;
        this.floor = 1;
        personInElevator = new HashSet<>();
        capacity = 6;
        up = true;
        waitTime = 400;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (requestTable) {
                if (requestTable.isEmpty() && personInElevator.isEmpty()) {
                    if (requestTable.isEnd()) {
                        return;
                    }
                    try {
                        requestTable.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                HashSet<PersonRequest> tmpOut = getOffElevator();
                if (personInElevator.size() == 0 && !isContainMore(up)) {
                    up = !up;
                }
                HashSet<PersonRequest> tmpIn = getInElevator();
                if (!(tmpIn.isEmpty() && tmpOut.isEmpty())) {
                    TimableOutput.println("OPEN-" + floor + "-" + id);
                    try {
                        sleep(waitTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (PersonRequest personRequest : tmpOut) {
                        TimableOutput.println("OUT-" + personRequest.getPersonId()
                                + "-" + floor + "-" + id);
                    }
                    for (PersonRequest personRequest : tmpIn) {
                        TimableOutput.println("IN-" + personRequest.getPersonId()
                                + "-" + floor + "-" + id);
                    }
                    TimableOutput.println("CLOSE-" + floor + "-" + id);
                }
            }
            if (isContainMore(up) || !personInElevator.isEmpty()) {
                //System.out.println(up);
                moveFloor();
            }
        }
    }

    public HashSet<PersonRequest> getInElevator() {
        HashSet<PersonRequest> tmpIn = new HashSet<>();
        for (PersonRequest personRequest : requestTable.getPersonRequests()) {
            if (personInElevator.size() == this.capacity) {
                break;
            }
            if (personRequest.getFromFloor() == this.floor &&
                    (personRequest.getToFloor() > floor) == up) {
                personInElevator.add(personRequest);
                tmpIn.add(personRequest);
            }
        }
        for (PersonRequest personRequest : tmpIn) {
            requestTable.removePersonRequest(personRequest);
        }
        return tmpIn;
    }

    public HashSet<PersonRequest> getOffElevator() {
        HashSet<PersonRequest> tmpOut = new HashSet<>();
        for (PersonRequest personRequest : personInElevator) {
            if (personRequest.getToFloor() == floor) {
                tmpOut.add(personRequest);
            }
        }
        for (PersonRequest personRequest : tmpOut) {
            personInElevator.remove(personRequest);
        }
        return tmpOut;
    }

    public void moveFloor() {
        if (up) {
            floor++;
        } else {
            floor--;
        }
        try {
            sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimableOutput.println("ARRIVE-" + floor + "-" + id);
    }

    public boolean isContainMore(boolean up) {
        for (PersonRequest personRequest : requestTable.getPersonRequests()) {
            if (up) {
                if (personRequest.getFromFloor() == floor && personRequest.getToFloor() > floor) {
                    return true;
                }
                if (personRequest.getFromFloor() > floor) {
                    return true;
                }
            } else {
                if (personRequest.getFromFloor() == floor && personRequest.getToFloor() < floor) {
                    return true;
                }
                if (personRequest.getFromFloor() < floor) {
                    return true;
                }
            }
        }
        return false;
    }
}
