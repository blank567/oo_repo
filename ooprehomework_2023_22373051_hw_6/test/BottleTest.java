import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BottleTest {

    Bottle bottle = new Bottle(1, "Water Bottle", 500);

    @Test
    public void getID() {
        assertEquals(bottle.getID(), 1);
    }

    @Test
    public void getName() {
        assertEquals(bottle.getName(), "Water Bottle");
    }

    @Test
    public void getCapacity() {
        assertEquals(bottle.getCapacity(), 500);
    }

    @Test
    public void setCapacity() {
        bottle.setCapacity(600);
        assertEquals(bottle.getCapacity(), 600);
    }
}