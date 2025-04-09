import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttackedTest {

    Attacked attacked = new Attacked("2000", "12", "l", "k", 1);

    @Test
    public void getYear() {
        assertEquals(attacked.getYear(), "2000");
    }

    @Test
    public void getMonth() {
        assertEquals(attacked.getMonth(), "12");
    }

    @Test
    public void getAttackedName() {
        assertEquals(attacked.getAttackedName(), "l");
    }

    @Test
    public void getEquipmetName() {
        assertEquals(attacked.getEquipmetName(), "k");
    }

    @Test
    public void getType() {
        assertEquals(attacked.getType(), 1);
    }
}