import com.oocourse.elevator1.PersonRequest;

import java.util.HashSet;

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
}
