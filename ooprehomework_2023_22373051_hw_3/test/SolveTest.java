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
        assertEquals(adventurers.size(),1);
    }

    @Test
    public void type2() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("2");
        input.add("1");
        input.add("1");
        input.add("Water");
        input.add("500");
        Solve.type2(input, adventurers);
    }

    @Test
    public void type3() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("3");
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
    }

    @Test
    public void type5() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("5");
        input.add("1");
        input.add("1");
        Solve.type5(input, adventurers);
    }

    @Test
    public void type6() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("6");
        input.add("1");
        input.add("1");
        Solve.type6(input, adventurers);

    }

    @Test
    public void type7() {
        ArrayList<Adventurer> adventurers = new ArrayList<>();
        ArrayList<String> input = new ArrayList<>();
        input.add("7");
        input.add("1");
        input.add("1");
        input.add("Water");
        input.add("500");
        Solve.type7(input, adventurers);
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
}