import com.oocourse.elevator2.PersonRequest;
import com.oocourse.elevator2.ResetRequest;
import com.oocourse.elevator2.TimableOutput;

import java.util.HashSet;

public class Elevator extends Thread {
    private final int id;//电梯id
    private final RequestTable requestTable;
    private int floor;
    private final HashSet<PersonRequest> personInElevator;
    private int capacity;
    private boolean up;
    private final int waitTime;
    private int floorTime;
    private RequestTable allRequestTables;
    private final ResetTable resetRequests;

    public Elevator(int id, RequestTable requestTable, RequestTable allRequestTables,
                    ResetTable resetRequests) {
        this.id = id;
        this.requestTable = requestTable;
        this.floor = 1;
        personInElevator = new HashSet<>();
        capacity = 6;
        up = true;
        waitTime = 400;
        floorTime = 400;
        this.allRequestTables = allRequestTables;
        this.resetRequests = resetRequests;
    }

    @Override
    public void run() {
        while (true) {
            processReset();
            synchronized (requestTable) {
                if (requestTable.isEmpty() && personInElevator.isEmpty()
                        && resetRequests.isEmpty()) {
                    if (requestTable.isEnd()) {
                        return;
                    }
                    try {
                        //TimableOutput.println(id);
                        requestTable.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //TimableOutput.println(id + "new");
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
                moveFloor();
            }
        }

    }

    public void processReset() {
        for (ResetRequest tmp : resetRequests.getResetRequests()) {
            if (tmp.getElevatorId() == this.id) {
                ResetRequest tmp1 = tmp;
                synchronized (requestTable) {
                    if (personInElevator.size() != 0) {
                        TimableOutput.println("OPEN-" + floor + "-" + id);
                        try {
                            sleep(waitTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (PersonRequest item : personInElevator) {
                            TimableOutput.println("OUT-" + item.getPersonId()
                                    + "-" + floor + "-" + id);
                            if (item.getToFloor() != floor) {
                                int toFloor = item.getToFloor();
                                int id = item.getPersonId();
                                PersonRequest personRequest = new PersonRequest(floor, toFloor, id);
                                allRequestTables.addPersonRequest(personRequest);
                            }
                        }
                        TimableOutput.println("CLOSE-" + floor + "-" + id);
                    }
                    for (PersonRequest item : requestTable.getPersonRequests()) {
                        allRequestTables.addPersonRequest(item);
                    }
                    requestTable.clear();
                    personInElevator.clear();
                    resetElevator(tmp.getCapacity(), tmp.getSpeed());
                    TimableOutput.println("RESET_BEGIN-" + id);
                    try {
                        sleep(1200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TimableOutput.println("RESET_END-" + id);
                    resetRequests.remove(tmp1);
                }
                break;
            }
        }
    }

    public void resetElevator(int newCapacity, double newFloorTime) {
        this.capacity = newCapacity;
        this.floorTime = (int) (newFloorTime * 1000);
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
            sleep(floorTime);
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

    public int getWhich(int fromFloor, int toFloor) throws InterruptedException {
        if (requestTable.isEmpty() && personInElevator.isEmpty()) {
            return Math.abs(fromFloor - this.floor) * (floorTime / 100);
        } else if ((toFloor > fromFloor) == this.up && requestTable.size() +
                personInElevator.size() < capacity) {
            if (up && fromFloor > this.floor) {
                return (fromFloor - this.floor) * 10 * (floorTime / 100);
            }
            if (!up && fromFloor < this.floor) {
                return (this.floor - fromFloor) * 10 * (floorTime / 100);
            }
            return 100;
        }
        return 100;
    }
}
