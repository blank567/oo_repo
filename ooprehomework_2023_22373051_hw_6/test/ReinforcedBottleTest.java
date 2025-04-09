import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReinforcedBottleTest {

    ReinforcedBottle reinforcedBottle = new ReinforcedBottle(1, "l", 2, 3, "ReinforcedBottle", 2.0);

    @Test
    public void getID() {
        assertEquals(reinforcedBottle.getID(), 1);
    }

    @Test
    public void getName() {
        assertEquals(reinforcedBottle.getName(), "l");
    }

    @Test
    public void getCapacity() {
        assertEquals(reinforcedBottle.getCapacity(), 2);
    }

    @Test
    public void setCapacity() {
        reinforcedBottle.setCapacity(100);
        assertEquals(reinforcedBottle.getCapacity(), 100);
    }

    @Test
    public void getPrice() {
        assertEquals(reinforcedBottle.getPrice(), 3);
    }

    @Test
    public void getType() {
        assertEquals(reinforcedBottle.getType(), "ReinforcedBottle");
    }

    @Test
    public void getRatio() {
        assertEquals(reinforcedBottle.getRatio(), 2.0, 1e-8);
    }
}