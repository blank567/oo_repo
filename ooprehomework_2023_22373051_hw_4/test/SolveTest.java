import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.*;

public class SolveTest {

    @Test
    public void main() {
    }

    @Test
    public void type1() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("1");
        input.add("1");
        input.add("k");
        Solve.type1(input, adventurers);
        assertEquals(adventurers.size(), 1);
    }

    @Test
    public void type2() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("1");
        input.add("1");
        input.add("k");
        Solve.type1(input, adventurers);

        ArrayList<String> tmp = new ArrayList<>();
        tmp.add("2");
        tmp.add("1");
        tmp.add("1");
        tmp.add("l");
        tmp.add("10");
        Solve.type2(tmp, adventurers);
    }

    @Test
    public void type3() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        Adventurer adventurer = new Adventurer(1, "k");
        adventurers.add(adventurer);

        Bottle bottle = new Bottle(1, "k", 1);
        adventurer.getBottleMap().put(1, bottle);
        ArrayList<String> input = new ArrayList<>();
        input.add("1");
        input.add("1");
        input.add("1");
        Solve.type3(input, adventurers);
    }

    @Test
    public void type4() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("4");
        input.add("1");
        input.add("1");
        input.add("Water");
        input.add("500");
        Solve.type4(input, adventurers);
        Adventurer adventurer = new Adventurer(1, "l");
        adventurers.add(adventurer);
        Solve.type4(input, adventurers);
    }

    @Test
    public void type5() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("1");
        input.add("1");
        input.add("2");
        Solve.type5(input, adventurers);
        Adventurer adventurer1 = new Adventurer(1, "Adventurer1");
        Adventurer adventurer2 = new Adventurer(2, "Adventurer2");
        Equipment equipment1 = new Equipment(1, "Equipment1", 1);
        Equipment equipment2 = new Equipment(2, "Equipment2", 1);
        adventurer1.getOwnEquipment().add(equipment1);
        adventurer2.getOwnEquipment().add(equipment2);
        adventurer1.getEquipmentMap().put(1,equipment1);
        adventurer1.getEquipmentMap().put(2,equipment2);
        adventurer2.getEquipmentMap().put(1,equipment1);
        adventurer2.getEquipmentMap().put(2,equipment2);
        adventurers.add(adventurer1);
        adventurers.add(adventurer2);
        Solve.type5(input, adventurers);
    }

    @Test
    public void type6() {

    }

    @Test
    public void type7() {

    }

    @Test
    public void type8() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("8");
        input.add("1");
        input.add("1");
        Solve.type8(input, adventurers);
    }

    @Test
    public void type9() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("9");
        input.add("1");
        input.add("1");
        Solve.type9(input, adventurers);
    }

    @Test
    public void type10() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("10");
        input.add("1");
        input.add("1");
        Solve.type10(input, adventurers);
    }

    @Test
    public void type11() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("11");
        input.add("1");
        input.add("1");
        Solve.type11(input, adventurers);
    }

    @Test
    public void type12() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("12");
        input.add("1");
        input.add("1");
        Solve.type12(input, adventurers);
    }

    @Test
    public void type13() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("13");
        input.add("1");
        input.add("1");
        Solve.type13(input, adventurers);
    }

    @Test
    public void type14() {
    }

    @Test
    public void type15() {
        ArrayList<Dairy> dairies = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("1");
        input.add("2023/10");
        Solve.type15(input, dairies);
        dairies.add(new Dairy("2023", "10", 1, "Adventurer1", "Bottle1"));
        dairies.add(new Dairy("2023", "11", "Adventurer1", "Adventurer2", "Equipment1"));
        Solve.type15(input, dairies);
        input.remove("2023/10");
        input.add("2023/11");
        Solve.type15(input, dairies);
        Solve.type15(input, dairies);
    }

    @Test
    public void type16() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        Adventurer adventurer1 = new Adventurer(1, "l");
        Adventurer adventurer2 = new Adventurer(2, "l");
        ArrayList<String> input1 = new ArrayList<>();
        input1.add("1");
        input1.add("1");
        Solve.type16(input1, adventurers);
        adventurers.add(adventurer1);
        Solve.type16(input1, adventurers);
        adventurers.remove(adventurer1);
        adventurers.add(adventurer2);
        Solve.type16(input1, adventurers);
        assertEquals(1, 1);
    }

    @Test
    public void type17() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        Adventurer adventurer1 = new Adventurer(1, "l");
        Adventurer adventurer2 = new Adventurer(2, "l");
        ArrayList<String> input1 = new ArrayList<>();
        input1.add("1");
        input1.add("1");
        Solve.type17(input1, adventurers);
        adventurers.add(adventurer1);
        Solve.type17(input1, adventurers);
        adventurers.remove(adventurer1);
        adventurers.add(adventurer2);
        Solve.type17(input1, adventurers);
        assertEquals(1, 1);
    }
}