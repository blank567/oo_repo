import com.oocourse.spec2.exceptions.AcquaintanceNotFoundException;

import java.util.HashMap;

public class MyAcquaintanceNotFoundException extends AcquaintanceNotFoundException {
    private int id;
    private static int countOfSum = 0;
    private static HashMap<Integer, Integer> countOfId = new HashMap<>();

    public MyAcquaintanceNotFoundException(int id) {
        this.id = id;
        countOfSum++;
        countOfId.merge(id, 1, Integer::sum);
    }

    @Override
    public void print() {
        System.out.println("anf-" + countOfSum + ", " + id + "-" + countOfId.get(id));
    }
}