import org.junit.Test;
import static org.junit.Assert.*;

public class AttackedTest {

    @Test
    public void getYear() {
        Attacked attacked = new Attacked("2000", "12", "l", "k", 1);
        assertEquals(attacked.getYear(), "2000");
    }

    @Test
    public void getMonth() {
        Attacked attacked = new Attacked("2000", "12", "l", "k", 1);
        assertEquals(attacked.getMonth(), "12");
    }

    @Test
    public void getAttackedName() {
        Attacked attacked = new Attacked("2000", "12", "l", "k", 1);
        assertEquals(attacked.getAttackedName(), "l");
    }

    @Test
    public void getEquipmetName() {
        Attacked attacked = new Attacked("2000", "12", "l", "k", 1);
        assertEquals(attacked.getEquipmetName(), "k");
    }

    @Test
    public void getType() {
        Attacked attacked = new Attacked("2000", "12", "l", "k", 1);
        assertEquals(attacked.getType(), 1);
    }
}