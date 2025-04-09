import com.oocourse.elevator3.PersonRequest;
import com.oocourse.elevator3.TimableOutput;

import java.util.HashSet;
import java.util.Iterator;

public class RequestTable {
    private HashSet<PersonRequest> personRequests;
    private boolean isEnd;
    private boolean isEmptyOfEle;
    private boolean isDouble;
    private int maxFloor;
    private int floor;

    public RequestTable() {
        this.personRequests = new HashSet<>();
        this.isEnd = false;
        isEmptyOfEle = true;
        isDouble = false;
        maxFloor = 11;
    }

    public synchronized void setIsEmptyOfEle(boolean isEmpty) {
        this.isEmptyOfEle = isEmpty;
        notifyAll();
    }

    public synchronized void setIsDouble(boolean isDouble) {
        this.isDouble = isDouble;
        notifyAll();
    }

    public synchronized void setMaxFloor(int maxFloor) {
        this.maxFloor = maxFloor;
        notifyAll();
    }

    public synchronized void setFloor(int floor) {
        this.floor = floor;
        notifyAll();
    }

    public synchronized int getFloor() {
        notifyAll();
        return this.floor;
    }

    public synchronized int getMaxFloor() {
        notifyAll();
        return this.maxFloor;
    }

    public synchronized boolean getIsDouble() {
        notifyAll();
        return isDouble;
    }

    public synchronized boolean getIsEmptyOfEle() {
        notifyAll();
        return this.isEmptyOfEle;
    }

    public synchronized HashSet<PersonRequest> getPersonRequests() {
        notifyAll();
        return personRequests;
    }

    public synchronized boolean isEnd() {
        notifyAll();
        return isEnd;
    }

    public synchronized void setEnd(boolean end) {
        isEnd = end;
        notifyAll();
    }

    public synchronized void addPersonRequest(PersonRequest personRequest,
                                              int id, int type, boolean isDouble, int floor) {
        if (type == 0) {
            if (!isDouble) {
                personRequests.add(personRequest);
                TimableOutput.println("RECEIVE-" + personRequest.getPersonId() + "-" + id);
            } else {
                if (personRequest.getFromFloor() < floor) {
                    personRequests.add(personRequest);
                    TimableOutput.println("RECEIVE-" + personRequest.getPersonId() +
                            "-" + id + "-A");
                }
                if (personRequest.getFromFloor() == floor && personRequest.getToFloor() < floor) {
                    personRequests.add(personRequest);
                    TimableOutput.println("RECEIVE-" + personRequest.getPersonId() +
                            "-" + id + "-A");
                }
            }
        }

        if (type == 1) {
            if (isDouble) {
                if (personRequest.getFromFloor() > floor) {
                    personRequests.add(personRequest);
                    TimableOutput.println("RECEIVE-" + personRequest.getPersonId() +
                            "-" + id + "-B");
                }
                if (personRequest.getFromFloor() == floor && personRequest.getToFloor() > floor) {
                    personRequests.add(personRequest);
                    TimableOutput.println("RECEIVE-" + personRequest.getPersonId() +
                            "-" + id + "-B");
                }
            }
        }
        notifyAll();
    }

    public synchronized void addPersonRequest(PersonRequest personRequest) {
        personRequests.add(personRequest);
        notifyAll();
    }

    public synchronized void removePersonRequest(PersonRequest personRequest) {
        personRequests.remove(personRequest);
        notifyAll();
    }

    public synchronized boolean isEmpty() {
        notifyAll();
        return personRequests.isEmpty();
    }

    public synchronized void clear() {
        personRequests.clear();
        notifyAll();
    }

    public synchronized int size() {
        notifyAll();
        return personRequests.size();
    }

    public synchronized PersonRequest getOneRequestAndRemove() {
        if (personRequests.isEmpty() && (!isEnd)) {
            try {
                wait();//等到request进入
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (!personRequests.isEmpty()) {
            Iterator<PersonRequest> iterator = personRequests.iterator();
            PersonRequest element = iterator.next();
            iterator.remove();
            notifyAll();
            return element;
        } else {
            notifyAll();
            return null;
        }
    }

    public synchronized void just() {
        notifyAll();
    }
}
