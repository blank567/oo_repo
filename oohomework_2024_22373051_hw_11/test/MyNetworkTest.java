import com.oocourse.spec3.main.EmojiMessage;
import com.oocourse.spec3.main.Message;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MyNetworkTest {

    private void createData(MyNetwork network, MyNetwork myNetwork) {
        MyPerson person = new MyPerson(1, "aaa", 1);
        MyPerson person1 = new MyPerson(2, "aaa", 2);
        MyPerson person2 = new MyPerson(1, "aaa", 1);
        MyPerson person3 = new MyPerson(2, "aaa", 2);
        try {
            myNetwork.addPerson(person);
            myNetwork.addPerson(person1);
            myNetwork.addRelation(1, 2, 1);
            network.addPerson(person2);
            network.addPerson(person3);
            network.addRelation(1, 2, 1);
        } catch (Exception e) {
            System.out.println("wrong person");
        }

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            try {
                myNetwork.storeEmojiId(i);
                network.storeEmojiId(i);
            } catch (Exception e) {
                i--;
            }
        }

        for (int i = 0; i < 1000; i++) {
            int emojiid = random.nextInt(100);
            Message message = new MyEmojiMessage(i, emojiid, person, person1);
            Message message1 = new MyEmojiMessage(i, emojiid, person2, person3);
            try {
                myNetwork.addMessage(message);
                network.addMessage(message1);
                myNetwork.sendMessage(i);
                network.sendMessage(i);
            } catch (Exception e) {
                System.out.println("wrong message");
            }
        }
        for (int i = 1000; i < 1500; i++) {
            Message message = new MyRedEnvelopeMessage(i, i, person, person1);
            Message message1 = new MyRedEnvelopeMessage(i, i, person2, person3);
            try {
                myNetwork.addMessage(message);
                network.addMessage(message1);
            } catch (Exception e) {
                System.out.println("wrong message");
            }
        }
        for (int i = 1500; i < 2000; i++) {
            Message message = new MyNoticeMessage(i, "hello", person, person1);
            Message message1 = new MyNoticeMessage(i, "hello", person2, person3);
            try {
                myNetwork.addMessage(message);
                network.addMessage(message1);
            } catch (Exception e) {
                System.out.println("wrong message");
            }
        }

        for (int i = 2000; i < 2500; i++) {
            Message message = new MyMessage(i, i, person, person1);
            Message message1 = new MyMessage(i, i, person2, person3);
            try {
                myNetwork.addMessage(message);
                network.addMessage(message1);
            } catch (Exception e) {
                System.out.println("wrong message");
            }
        }

        for (int i = 2500; i < 3500; i++) {
            int id = random.nextInt(100);
            Message message = new MyEmojiMessage(i, id, person, person1);
            Message message1 = new MyEmojiMessage(i, id, person2, person3);
            try {
                myNetwork.addMessage(message);
                network.addMessage(message1);
            } catch (Exception e) {
                System.out.println("wrong message");
            }
        }
    }

    @Test
    public void deleteColdEmoji() {
        for (int i = 0; i < 101; i++) {
            test(i);
        }
        test(2000);
        test(50000);
    }

    public void test(int limit) {
        MyNetwork oldNetwork = new MyNetwork();
        MyNetwork newNetwork = new MyNetwork();
        createData(oldNetwork, newNetwork);
        int[] oldEmojiIdList = oldNetwork.getEmojiIdList();
        int[] oldEmojiHeatList = oldNetwork.getEmojiHeatList();
        Message[] oldMessages = oldNetwork.getMessages();

        int sum = 0;
        for (int i = 0; i < oldEmojiHeatList.length; i++) {
            if (oldEmojiHeatList[i] >= limit) {
                sum++;
            }
        }
        assertEquals(sum, newNetwork.deleteColdEmoji(limit));

        int[] newEmojiIdList = newNetwork.getEmojiIdList();
        int[] newEmojiHeatList = newNetwork.getEmojiHeatList();
        Message[] newMessages = newNetwork.getMessages();

        int cnt = 0;
        for (int i = 0; i < oldEmojiIdList.length; i++) {
            if (oldEmojiHeatList[i] < limit) {
                continue;
            }
            for (int j = 0; j < newEmojiIdList.length; j++) {
                if (oldEmojiHeatList[i] == newEmojiHeatList[j]) {
                    cnt++;
                    break;
                }
            }
        }
        assertEquals(cnt, sum);
        cnt = 0;

        for (int i = 0; i < newEmojiIdList.length; i++) {
            for (int j = 0; j < oldEmojiIdList.length; j++) {
                if (newEmojiHeatList[i] == oldEmojiHeatList[j]) {
                    cnt++;
                    break;
                }
            }
        }
        assertEquals(cnt, sum);
        cnt = 0;

        sum = 0;
        for (int i = 0; i < oldMessages.length; i++) {
            if (oldMessages[i] instanceof EmojiMessage && !newNetwork.containsEmojiId(((EmojiMessage) oldMessages[i]).getEmojiId())) {
                continue;
            }
            sum++;
        }
        assertEquals(sum, newMessages.length);

        for (int i = 0; i < oldMessages.length; i++) {
            if (oldMessages[i] instanceof EmojiMessage && !newNetwork.containsEmojiId(((EmojiMessage) oldMessages[i]).getEmojiId())) {
                continue;
            }
            for (int j = 0; j < newMessages.length; j++) {
                if (oldMessages[i].equals(newMessages[j])) {
                    cnt++;
                    break;
                }
            }
        }
        assertEquals(cnt, sum);
        cnt = 0;

        for (int i = 0; i < newMessages.length; i++) {
            for (int j = 0; j < oldMessages.length; j++) {
                if (newMessages[i].equals(oldMessages[j])) {
                    cnt++;
                    break;
                }
            }
        }
        assertEquals(cnt, sum);
    }
}