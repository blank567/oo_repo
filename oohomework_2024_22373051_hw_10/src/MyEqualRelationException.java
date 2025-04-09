import com.oocourse.spec2.exceptions.EqualRelationException;

import java.util.HashMap;

public class MyEqualRelationException extends EqualRelationException {
    private int id1;
    private int id2;
    private static int countOfSum = 0;
    private static HashMap<Integer, Integer> countOfId = new HashMap<>();

    public MyEqualRelationException(int id1, int id2) {
        this.id1 = Math.min(id1, id2);
        this.id2 = Math.max(id1, id2);
        countOfId.merge(id1, 1, Integer::sum);
        if (id2 != id1) {
            countOfId.merge(id2, 1, Integer::sum);
        }
        countOfSum++;
    }

    @Override
    public void print() {
        System.out.println("er-" + countOfSum + ", " + id1 + "-" +
                countOfId.get(id1) + ", " + id2 + "-" + countOfId.get(id2));
    }
}
