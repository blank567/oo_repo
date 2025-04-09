import com.oocourse.elevator3.DoubleCarResetRequest;
import com.oocourse.elevator3.NormalResetRequest;
import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.TimableOutput;

import java.util.ArrayList;
import java.util.HashSet;

public class Elevator extends Thread { //A
    private final int id;//电梯id
    private final RequestTable requestTable;
    private final RequestTable newRequestTable;
    private int floor;
    private final HashSet<PersonRequest> personInElevator;
    private int capacity;
    private boolean up;
    private final int waitTime;
    private int floorTime;
    private RequestTable allRequestTables;
    private final ResetTable resetRequests;
    private int maxFloor;
    private boolean isDouble;
    private Object lock;

    public Elevator(int id, RequestTable requestTable, RequestTable allRequestTables,
                    ResetTable resetRequests, RequestTable newRequestTable) {
        this.id = id;
        this.requestTable = requestTable;
        this.newRequestTable = newRequestTable;
        this.floor = 1;
        personInElevator = new HashSet<>();
        capacity = 6;
        up = true;
        waitTime = 400;
        floorTime = 400;
        this.allRequestTables = allRequestTables;
        this.resetRequests = resetRequests;
        this.maxFloor = 11;
        this.isDouble = false;
        this.lock = new Object();
    }

    @Override
    public void run() {
        while (true) {
            if (!isDouble) {
                processReset();
            }
            if (requestTable.isEmpty() && personInElevator.isEmpty()) {
                if (requestTable.isEnd() && newRequestTable.isEmpty()
                        && newRequestTable.getIsEmptyOfEle()) {
                    return;
                }
                synchronized (requestTable) {
                    try {
                        requestTable.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (requestTable) {
                synchronized (newRequestTable) {
                    HashSet<PersonRequest> tmpOut = getOffElevator();
                    HashSet<PersonRequest> tmpOut1 = getOffElevator1();
                    if ((personInElevator.size() == 0 && !isContainMore(up)) || floor == maxFloor) {
                        up = !up;
                    }
                    HashSet<PersonRequest> tmpIn = getInElevator();
                    requestTable.setIsEmptyOfEle(personInElevator.isEmpty());
                    if (!(tmpIn.isEmpty() && tmpOut.isEmpty() && tmpOut1.isEmpty())) {
                        TimableOutput.println("OPEN-" + floor + "-" + id + (isDouble ? "-A" : ""));
                        try {
                            sleep(waitTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (PersonRequest personRequest : tmpOut) {
                            TimableOutput.println("OUT-" + personRequest.getPersonId()
                                    + "-" + floor + "-" + id + (isDouble ? "-A" : ""));
                        }
                        for (PersonRequest personRequest : tmpOut1) {
                            TimableOutput.println("OUT-" + personRequest.getPersonId()
                                    + "-" + floor + "-" + id + (isDouble ? "-A" : ""));
                            TimableOutput.println("RECEIVE-" + personRequest.getPersonId() +
                                    "-" + id + "-B");
                        }
                        for (PersonRequest personRequest : tmpIn) {
                            TimableOutput.println("IN-" + personRequest.getPersonId()
                                    + "-" + floor + "-" + id + (isDouble ? "-A" : ""));
                        }
                        TimableOutput.println("CLOSE-" + floor + "-" + id + (isDouble ? "-A" : ""));
                    }
                }
            }
            if (isContainMore(up) || !personInElevator.isEmpty()
                    || (floor == maxFloor && isDouble)) {
                moveFloor();
            }
        }
    }

    public void realProcess() {
        ArrayList<PersonRequest> tmp = new ArrayList<>();
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
                    tmp.add(personRequest);
                    //allRequestTables.addPersonRequest(personRequest);
                }
            }
            TimableOutput.println("CLOSE-" + floor + "-" + id);
        }
        for (PersonRequest item : requestTable.getPersonRequests()) {
            tmp.add(item);
            //allRequestTables.addPersonRequest(item);
        }
        requestTable.clear();
        personInElevator.clear();
        TimableOutput.println("RESET_BEGIN-" + id);
        try {
            sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TimableOutput.println("RESET_END-" + id);
        for (PersonRequest item : tmp) {
            allRequestTables.addPersonRequest(item);
        }
    }

    public void processReset() {
        for (DoubleCarResetRequest tmp : resetRequests.getDoubleCarResetRequests()) {
            if (tmp.getElevatorId() == this.id) {
                synchronized (requestTable) {
                    this.maxFloor = tmp.getTransferFloor();
                    this.isDouble = true;
                    requestTable.setIsDouble(true);
                    requestTable.setMaxFloor(this.maxFloor);
                    realProcess();
                    resetElevator(tmp.getCapacity(), tmp.getSpeed());
                    resetRequests.removeDoubleCarResetRequest(tmp);
                    this.floor = tmp.getTransferFloor() - 1;
                    requestTable.setFloor(floor);
                    NewElevator newElevator = new NewElevator(id, requestTable, capacity, floorTime,
                            tmp.getTransferFloor(), newRequestTable, lock);
                    newElevator.start();
                }
                break;
            }
        }

        for (NormalResetRequest tmp : resetRequests.getNormalResetRequests()) {
            if (tmp.getElevatorId() == this.id) {
                synchronized (requestTable) {
                    realProcess();
                    resetElevator(tmp.getCapacity(), tmp.getSpeed());
                    resetRequests.removeNormalResetRequest(tmp);
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

    public HashSet<PersonRequest> getOffElevator1() {
        HashSet<PersonRequest> tmpOut = new HashSet<>();
        for (PersonRequest personRequest : personInElevator) {
            if (floor == maxFloor) {
                tmpOut.add(personRequest);
                PersonRequest tmp = new PersonRequest(floor, personRequest.getToFloor(),
                        personRequest.getPersonId());
                newRequestTable.addPersonRequest(tmp);
            }
        }
        for (PersonRequest personRequest : tmpOut) {
            personInElevator.remove(personRequest);
        }
        return tmpOut;
    }

    public void moveFloor() {
        try {
            sleep(floorTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (lock) {
            if (up && this.floor == maxFloor - 1 && newRequestTable.getFloor() == maxFloor) {
                return;
            } else if (up) {
                floor++;
            } else {
                floor--;
            }
            requestTable.setFloor(floor);
            TimableOutput.println("ARRIVE-" + floor + "-" + id + (isDouble ? "-A" : ""));
        }
    }

    public boolean isContainMore(boolean up) {
        synchronized (requestTable) {
            for (PersonRequest personRequest : requestTable.getPersonRequests()) {
                if (up) {
                    if (personRequest.getFromFloor() == floor
                            && personRequest.getToFloor() > floor) {
                        return true;
                    }
                    if (personRequest.getFromFloor() > floor) {
                        return true;
                    }
                } else {
                    if (personRequest.getFromFloor() == floor
                            && personRequest.getToFloor() < floor) {
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
}
