import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class MethodTest {
    @Test
    public void process() {
        ArrayList<String> res = new ArrayList<>();
        String string1 = "2023/10-Name-Description";
        String string2 = "2023/11-Name@OtherName-Description";
        String string3 = "2023/12-Name@#-Description";
        String string4 = "2023/01";
        res.add("1");
        res.add("2023");
        res.add("10");
        res.add("Name");
        res.add("Description");
        assertEquals(res, Method.process(string1));
        res.clear();
        res.add("2");
        res.add("2023");
        res.add("11");
        res.add("Name");
        res.add("OtherName");
        res.add("Description");
        assertEquals(res, Method.process(string2));
        res.clear();
        res.add("3");
        res.add("2023");
        res.add("12");
        res.add("Name");
        res.add("Description");
        assertEquals(res, Method.process(string3));
        Method.process(string4);
        res.clear();
        res.add("2023");
        res.add("01");
        assertEquals(res, Method.process(string4));
    }

    @Test
    public void judge() {
        ArrayList<String> names = new ArrayList<>();
        String name1 = "l";
        String name2 = "p";
        names.add(name1);
        assertEquals(Method.judge(name1, names),true);
        assertEquals(Method.judge(name2, names),false);
    }

    @Test
    public void judgeBot() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<Dairy> dairies = new ArrayList<>();
        Method.judgeBot("1", adventurers, "2", dairies, "2001", "12");
        Adventurer adventurer1 = new Adventurer(1, "Adventurer1");
        adventurers.add(adventurer1);
        Bottle bottle1 = new Bottle(1, "Bottle1", 0);
        Bottle bottle2 = new Bottle(2, "Bottle2", 200);
        adventurer1.getOwnBottleMap().put(1, bottle1);
        adventurer1.getOwnBottleMap().put(2, bottle2);
        Method.judgeBot("Bottle1", adventurers, "Adventurer1", dairies, "2023", "10");
        Method.judgeBot("Bottle2", adventurers, "Adventurer1", dairies, "2023", "10");
        Method.judgeBot("NonExistentBottle", adventurers, "Adventurer1", dairies, "2023", "10");
        Method.judgeBot("Bottle1", adventurers, "Adventurer2", dairies, "2023", "10");

    }

    @Test
    public void judgeEqu1() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<Dairy> dairies = new ArrayList<>();
        //ArrayList<String> names=new ArrayList<>();
        Method.judgeEqu("1", adventurers, "2", "2", "2001", "12", dairies);
        Adventurer adventurer1 = new Adventurer(1, "Adventurer1");
        Adventurer adventurer2 = new Adventurer(1, "Adventurer2");
        adventurers.add(adventurer1);
        adventurers.add(adventurer2);

        Equipment equipment1 = new Equipment(1, "Equipment1", 1);
        Equipment equipment2 = new Equipment(2, "Equipment2", 1);
        adventurer1.getOwnEquipment().add(equipment1);
        adventurer2.getOwnEquipment().add(equipment2);

        Method.judgeEqu("Equipment1", adventurers, "Adventurer1", "Adventurer2", "2023", "10", dairies);
        Method.judgeEqu("Equipment2", adventurers, "Adventurer2", "Adventurer1", "2023", "11", dairies);
        Method.judgeEqu("NonExistentEquipment", adventurers, "Adventurer1", "Adventurer2", "2023", "12", dairies);
    }

    @Test
    public void judgeEqu2() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<Dairy> dairies = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        Method.judgeEqu("1", adventurers, "2", "2001", "12", names, dairies);
        Adventurer adventurer1 = new Adventurer(1, "Adventurer1");
        Adventurer adventurer2 = new Adventurer(1, "Adventurer2");
        adventurers.add(adventurer1);
        adventurers.add(adventurer2);
        Equipment equipment1 = new Equipment(1, "Equipment1", 1);
        Equipment equipment2 = new Equipment(2, "Equipment2", 1);
        adventurer1.getOwnEquipment().add(equipment1);
        adventurer2.getOwnEquipment().add(equipment2);
        names.add("Adventurer2");
        names.add("Adventurer3");
        Method.judgeEqu("Equipment1", adventurers, "Adventurer1", "2023", "10", names, dairies);
        Method.judgeEqu("Equipment2", adventurers, "Adventurer2", "2023", "11", names, dairies);
        Method.judgeEqu("NonExistentEquipment", adventurers, "Adventurer1", "2023", "12", names, dairies);
    }
}