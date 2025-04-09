import com.oocourse.spec3.exceptions.MessageIdNotFoundException;

import java.util.HashMap;

public class MyMessageIdNotFoundException extends MessageIdNotFoundException {
    private int id;
    private static int countOfSum = 0;
    private static HashMap<Integer, Integer> countOfId = new HashMap<>();

    public MyMessageIdNotFoundException(int id) {
        this.id = id;
        countOfSum++;
        countOfId.merge(id, 1, Integer::sum);
    }

    @Override
    public void print() {
        System.out.println("minf-" + countOfSum + ", " + id + "-" + countOfId.get(id));
    }
}
