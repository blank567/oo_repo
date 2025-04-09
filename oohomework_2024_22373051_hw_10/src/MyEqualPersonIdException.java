import com.oocourse.spec2.exceptions.EqualPersonIdException;

import java.util.HashMap;

public class MyEqualPersonIdException extends EqualPersonIdException {
    private int id;
    private static int countOfSum = 0;
    private static HashMap<Integer, Integer> countOfId = new HashMap<>();

    public MyEqualPersonIdException(int id) {
        this.id = id;
        countOfSum++;
        countOfId.merge(id, 1, Integer::sum);
    }

    @Override
    public void print() {
        System.out.println("epi-" + countOfSum + ", " + id + "-" + countOfId.get(id));
    }
}
