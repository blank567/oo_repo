import com.oocourse.spec1.main.Person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyNetworkTest {
    MyNetwork network = new MyNetwork();

    @org.junit.Test
    public void queryTripleSum() {
        try {
            network.addPerson(new MyPerson(1, "1", 1));
            network.addPerson(new MyPerson(2, "2", 2));
            network.addPerson(new MyPerson(3, "3", 3));
            network.addPerson(new MyPerson(4, "4", 4));
            network.addPerson(new MyPerson(5, "5", 5));
            network.addPerson(new MyPerson(6, "6", 6));
            network.addPerson(new MyPerson(7, "7", 6));
            network.addPerson(new MyPerson(8, "8", 6));
            network.addPerson(new MyPerson(9, "9", 6));
            network.addPerson(new MyPerson(10, "10", 6));
        } catch (Exception e) {
            System.out.println();
        }

        try {
            network.addRelation(3, 7, -730);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(6, 3, -581);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(10, 1, -597);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 7, -354);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(3, 6, -667);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(3, 1, -697);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(10, 5, -607);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(6, 4, -566);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(3, 10, -137);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(4, 4, -592);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(6, 2, -369);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(4, 4, -147);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(9, 8, -695);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(10, 3, -275);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(6, 4, -447);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(7, 4, -177);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(5, 10, -93);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(6, 4, -686);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 9, -743);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(8, 1, -704);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(10, 1, -722);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 8, -117);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(5, 1, -130);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(4, 3, -329);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(10, 1, -241);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(6, 8, -352);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(1, 3, -347);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(9, 2, -9);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 3, -328);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(6, 4, -448);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(9, 7, -288);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(2, 7, -281);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(9, 2, -602);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(3, 2, -293);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(9, 3, -990);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(8, 4, -948);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(10, 3, -715);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 5, -592);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(5, 9, -123);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(4, 9, -122);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(9, 2, -505);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(9, 8, -233);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(9, 9, -534);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(5, 9, -52);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(9, 1, -805);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(8, 8, -284);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(10, 1, -425);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(3, 2, -36);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(8, 10, -758);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(4, 5, -120);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(3, 9, -497);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(6, 5, -957);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(5, 10, -602);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(5, 4, -914);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(6, 1, -972);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(8, 10, -371);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(10, 1, -909);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(7, 4, -649);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(1, 6, -593);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 7, -182);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 2, -753);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(10, 10, -61);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(4, 8, -912);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(2, 6, -602);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(4, 9, -242);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(3, 7, -507);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(1, 5, -95);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(1, 1, -757);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(2, 7, -395);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(5, 6, -127);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(4, 6, -628);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 10, -468);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(10, 7, -936);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(8, 7, -756);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(4, 7, -555);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(9, 2, -776);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(8, 7, -792);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(4, 8, -608);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(1, 8, -741);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(8, 2, -279);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(3, 8, -244);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(4, 6, -165);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(3, 7, -552);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(6, 5, -839);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(2, 10, -376);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(4, 2, -669);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(4, 8, -785);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(7, 9, -79);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.modifyRelation(3, 4, -677);
        } catch (Exception e) {
            System.out.println();
        }
        try {
            network.addRelation(10, 2, -288);
        } catch (Exception e) {
            System.out.println();
        }
        Person person[] = network.getPersons();
        int n = person.length;
        int sum = 0;
        for (int i = 2; i < n; i++) {
            for (int j = 1; j < i; j++) {
                for (int k = 0; k < j; k++) {
                    if (network.getPerson(person[i].getId()).isLinked(network.getPerson(person[j].getId())) &&
                            network.getPerson(person[j].getId()).isLinked(network.getPerson(person[k].getId())) &&
                            network.getPerson(person[k].getId()).isLinked(network.getPerson(person[i].getId()))) {
                        sum++;
                    }
                }
            }
        }
        assertEquals(sum, network.queryTripleSum());
        for (int i = 0; i < person.length; i++) {
            assertTrue(((MyPerson) network.getPersons()[i]).strictEquals(person[i]));
        }
        assertEquals(person.length, network.getPersons().length);
    }
}