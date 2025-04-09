import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.TimableOutput;

import java.util.HashSet;

public class NewElevator extends Thread {
    private final int id;//电梯id
    private final RequestTable requestTable;
    private final RequestTable newRequestTable;
    private int floor;
    private final HashSet<PersonRequest> personInElevator;
    private int capacity;
    private boolean up;
    private final int waitTime;
    private int floorTime;
    private int minFloor;
    private Object lock;

    public NewElevator(int id, RequestTable requestTable, int capacity, int floorTime, int minFloor
            , RequestTable newRequestTable, Object lock) {
        this.id = id;
        this.requestTable = requestTable;
        this.floor = minFloor + 1;
        personInElevator = new HashSet<>();
        this.capacity = capacity;
        this.up = false;
        this.waitTime = 400;
        this.floorTime = floorTime;
        this.minFloor = minFloor;
        this.newRequestTable = newRequestTable;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            if (personInElevator.isEmpty() && newRequestTable.isEmpty()) {
                if (requestTable.isEnd() && requestTable.isEmpty()
                        && requestTable.getIsEmptyOfEle()) {
                    return;
                }
                synchronized (newRequestTable) {
                    try {
                        newRequestTable.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            synchronized (requestTable) {
                synchronized (newRequestTable) {
                    HashSet<PersonRequest> tmpOut = getOffElevator();
                    HashSet<PersonRequest> tmpOut1 = getOffElevator1();
                    if ((personInElevator.size() == 0 && !isContainMore(up)) || floor == minFloor) {
                        up = !up;
                    }
                    HashSet<PersonRequest> tmpIn = getInElevator();
                    newRequestTable.setIsEmptyOfEle(personInElevator.isEmpty());
                    if (!(tmpIn.isEmpty() && tmpOut.isEmpty() && tmpOut1.isEmpty())) {
                        TimableOutput.println("OPEN-" + floor + "-" + id + "-B");
                        try {
                            sleep(waitTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        for (PersonRequest personRequest : tmpOut) {
                            TimableOutput.println("OUT-" + personRequest.getPersonId()
                                    + "-" + floor + "-" + id + "-B");
                        }
                        for (PersonRequest personRequest : tmpOut1) {
                            TimableOutput.println("OUT-" + personRequest.getPersonId()
                                    + "-" + floor + "-" + id + "-B");
                            TimableOutput.println("RECEIVE-" + personRequest.getPersonId() +
                                    "-" + id + "-A");
                        }
                        for (PersonRequest personRequest : tmpIn) {
                            TimableOutput.println("IN-" + personRequest.getPersonId()
                                    + "-" + floor + "-" + id + "-B");
                        }
                        TimableOutput.println("CLOSE-" + floor + "-" + id + "-B");
                    }
                }
            }
            if (isContainMore(up) || !personInElevator.isEmpty() || floor == minFloor) {
                moveFloor();
            }
        }
    }

    public HashSet<PersonRequest> getInElevator() {
        HashSet<PersonRequest> tmpIn = new HashSet<>();
        for (PersonRequest personRequest : newRequestTable.getPersonRequests()) {
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
            newRequestTable.removePersonRequest(personRequest);
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
            if (floor == minFloor) {
                tmpOut.add(personRequest);
                PersonRequest tmp = new PersonRequest(floor, personRequest.getToFloor(),
                        personRequest.getPersonId());
                requestTable.addPersonRequest(tmp);
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
            if (requestTable.getFloor() == minFloor && !up && floor == minFloor + 1) {
                return;
            } else if (up) {
                floor++;
            } else {
                floor--;
            }
            newRequestTable.setFloor(floor);
            TimableOutput.println("ARRIVE-" + floor + "-" + id + "-B");
        }

    }

    public boolean isContainMore(boolean up) {
        synchronized (requestTable) {
            synchronized (newRequestTable) {
                for (PersonRequest personRequest : newRequestTable.getPersonRequests()) {
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
}
