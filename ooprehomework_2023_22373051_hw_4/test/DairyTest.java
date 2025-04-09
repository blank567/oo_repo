import org.junit.Test;
import static org.junit.Assert.*;

public class DairyTest {

    @Test
    public void getYear() {
        Dairy dairy = new Dairy("2000", "12", "l", "k", "k");
        assertEquals(dairy.getYear(), "2000");
    }

    @Test
    public void getMonth() {
        Dairy dairy = new Dairy("2000", "12", "l", "k", "k");
        assertEquals(dairy.getMonth(), "12");
    }

    @Test
    public void getType() {
        Dairy dairy = new Dairy("2000", "12", 1, "k", "k");
        assertEquals(dairy.getType(), 1);
    }

    @Test
    public void getAdvName2() {
        Dairy dairy = new Dairy("2000", "12", "l", "k", "k");
        assertEquals(dairy.getAdvName2(), "k");
    }

    @Test
    public void getAdvName1() {
        Dairy dairy = new Dairy("2000", "12", "l", "k", "k");
        assertEquals(dairy.getAdvName1(), "l");
    }

    @Test
    public void getName() {
        Dairy dairy = new Dairy("2000", "12", "l", "k", "k");
        assertEquals(dairy.getName(), "k");
    }
}