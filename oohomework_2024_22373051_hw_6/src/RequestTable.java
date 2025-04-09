import com.oocourse.elevator2.PersonRequest;
import com.oocourse.elevator2.TimableOutput;

import java.util.HashSet;
import java.util.Iterator;

public class RequestTable {
    private HashSet<PersonRequest> personRequests;
    private boolean isEnd;

    public RequestTable() {
        this.personRequests = new HashSet<>();
        this.isEnd = false;
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

    public synchronized void addPersonRequest(PersonRequest personRequest, int id) {
        personRequests.add(personRequest);
        TimableOutput.println("RECEIVE-" + personRequest.getPersonId() + "-" + id);
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
