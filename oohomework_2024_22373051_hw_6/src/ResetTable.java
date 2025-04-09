import com.oocourse.elevator2.ResetRequest;

import java.util.ArrayList;

public class ResetTable {
    private ArrayList<ResetRequest> resetRequests;

    public ResetTable() {
        resetRequests = new ArrayList<>();
    }

    public synchronized void addResetRequest(ResetRequest resetRequest) {
        resetRequests.add(resetRequest);
        notifyAll();
    }

    public synchronized void remove(ResetRequest resetRequest) {
        resetRequests.remove(resetRequest);
        notifyAll();
    }

    public synchronized boolean isEmpty() {
        notifyAll();
        return this.resetRequests.isEmpty();
    }

    public synchronized ArrayList<ResetRequest> getResetRequests() {
        notifyAll();
        return this.resetRequests;
    }
}
