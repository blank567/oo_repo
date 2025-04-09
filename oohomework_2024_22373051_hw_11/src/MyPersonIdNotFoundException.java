import com.oocourse.spec3.exceptions.PersonIdNotFoundException;

import java.util.HashMap;

public class MyPersonIdNotFoundException extends PersonIdNotFoundException {
    private int id;
    private static int countOfSum = 0;
    private static HashMap<Integer, Integer> countOfId = new HashMap<>();

    public MyPersonIdNotFoundException(int id) {
        this.id = id;
        countOfSum++;
        countOfId.merge(id, 1, Integer::sum);
    }

    @Override
    public void print() {
        System.out.println("pinf-" + countOfSum + ", " + id + "-" + countOfId.get(id));
    }
}
