import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegularBottleTest {

    RegularBottle regularBottle = new RegularBottle(1, "l", 2, 3, "RegularBottle");

    @Test
    public void getID() {
        assertEquals(regularBottle.getID(), 1);
    }

    @Test
    public void getName() {
        assertEquals(regularBottle.getName(), "l");
    }

    @Test
    public void getCapacity() {
        assertEquals(regularBottle.getCapacity(), 2);
    }

    @Test
    public void getPrice() {
        assertEquals(regularBottle.getPrice(), 3);
    }

    @Test
    public void getType() {
        assertEquals(regularBottle.getType(), "RegularBottle");
    }
}