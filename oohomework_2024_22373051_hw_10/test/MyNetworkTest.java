import com.oocourse.spec2.exceptions.AcquaintanceNotFoundException;
import com.oocourse.spec2.exceptions.PersonIdNotFoundException;
import com.oocourse.spec2.main.Person;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyNetworkTest {
    MyNetwork network = new MyNetwork();
    MyNetwork myNetwork = new MyNetwork();

    private MyNetwork createData(int n) {
        for (int i = 1; i < 100; i++) {
            Person person = new MyPerson(i, "aaa", i);
            Person person1 = new MyPerson(i, "aaa", i);
            try {
                myNetwork.addPerson(person);
                network.addPerson(person1);
            } catch (Exception e) {
                System.out.println("wrong person");
            }
        }
        //int n = 2500;
        Random randomOfId = new Random();
        Random randomOfValue = new Random();
        for (int i = 0; i < n; i++) {
            try {
                int id1 = randomOfId.nextInt(80);
                int id2 = randomOfId.nextInt(90);
                int value = randomOfValue.nextInt(200);
                myNetwork.addRelation(id1, id2, value);
                network.addRelation(id1, id2, value);
            } catch (Exception e) {
                i--;
            }
        }
        return myNetwork;
    }

    @org.junit.Test
    public void queryCoupleSum() throws AcquaintanceNotFoundException, PersonIdNotFoundException {
        MyNetwork newNetwork = createData(2000);
        Person person[] = network.getPersons();
        int n = person.length;
        int sum = 0;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                Person person1 = person[i];
                Person person2 = person[j];
                try {
                    int id1 = network.queryBestAcquaintance(person1.getId());
                    int id2 = network.queryBestAcquaintance(person2.getId());
                    if (id1 == person2.getId() && id2 == person1.getId()) {
                        sum++;
                    }
                } catch (Exception e) {
                }
            }
        }
        assertEquals(sum, newNetwork.queryCoupleSum());
        for (int i = 0; i < person.length; i++) {
            assertTrue(((MyPerson) newNetwork.getPersons()[i]).strictEquals(person[i]));
        }
        assertEquals(person.length, newNetwork.getPersons().length);
    }
}