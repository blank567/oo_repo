import com.oocourse.elevator3.DoubleCarResetRequest;
import com.oocourse.elevator3.NormalResetRequest;

import java.util.ArrayList;

public class ResetTable {
    private ArrayList<NormalResetRequest> normalResetRequests;
    private ArrayList<DoubleCarResetRequest> doubleCarResetRequests;

    public ResetTable() {
        normalResetRequests = new ArrayList<>();
        doubleCarResetRequests = new ArrayList<>();
    }

    public synchronized void addNormalResetRequest(NormalResetRequest resetRequest) {
        normalResetRequests.add(resetRequest);
        notifyAll();
    }

    public synchronized void addDoubleCarResetRequest(DoubleCarResetRequest doubleCarResetRequest) {
        doubleCarResetRequests.add(doubleCarResetRequest);
        notifyAll();
    }

    public synchronized void removeNormalResetRequest(NormalResetRequest resetRequest) {
        normalResetRequests.remove(resetRequest);
        notifyAll();
    }

    public synchronized void removeDoubleCarResetRequest(
            DoubleCarResetRequest doubleCarResetRequest) {
        doubleCarResetRequests.remove(doubleCarResetRequest);
        notifyAll();
    }

    public synchronized boolean isEmpty() {
        notifyAll();
        return this.normalResetRequests.isEmpty() && this.doubleCarResetRequests.isEmpty();
    }

    public synchronized ArrayList<NormalResetRequest> getNormalResetRequests() {
        notifyAll();
        return this.normalResetRequests;
    }

    public synchronized ArrayList<DoubleCarResetRequest> getDoubleCarResetRequests() {
        notifyAll();
        return this.doubleCarResetRequests;
    }
}
