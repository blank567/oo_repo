import com.oocourse.spec2.exceptions.RelationNotFoundException;

import java.util.HashMap;

public class MyRelationNotFoundException extends RelationNotFoundException {
    private int id1;
    private int id2;
    private static int countOfSum = 0;
    private static HashMap<Integer, Integer> countOfId = new HashMap<>();

    public MyRelationNotFoundException(int id1, int id2) {
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
        System.out.println("rnf-" + countOfSum + ", " + id1 + "-" +
                countOfId.get(id1) + ", " + id2 + "-" + countOfId.get(id2));
    }
}
