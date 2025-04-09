import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecoverBottleTest {
    RecoverBottle recoverBottle = new RecoverBottle(1, "l", 2, 3, "RecoverBottle", 4.0);

    @Test
    public void getID() {
        assertEquals(recoverBottle.getID(), 1);
    }

    @Test
    public void getName() {
        assertEquals(recoverBottle.getName(), "l");
    }

    @Test
    public void getCapacity() {
        assertEquals(recoverBottle.getCapacity(), 2);
    }

    @Test
    public void getPrice() {
        assertEquals(recoverBottle.getPrice(), 3);
    }

    @Test
    public void getType() {
        assertEquals(recoverBottle.getType(), "RecoverBottle");
    }

    @Test
    public void getRatio() {
        assertEquals(recoverBottle.getRatio(), 4.0, 1e-8);
    }
}