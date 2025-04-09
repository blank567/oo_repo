import com.oocourse.spec3.exceptions.EqualEmojiIdException;

import java.util.HashMap;

public class MyEqualEmojiIdException extends EqualEmojiIdException {
    private int id;
    private static int countOfSum = 0;
    private static HashMap<Integer, Integer> countOfId = new HashMap<>();

    public MyEqualEmojiIdException(int id) {
        this.id = id;
        countOfSum++;
        countOfId.merge(id, 1, Integer::sum);
    }

    @Override
    public void print() {
        System.out.println("eei-" + countOfSum + ", " + id + "-" + countOfId.get(id));
    }
}
