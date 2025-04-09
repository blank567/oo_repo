import org.junit.Test;
import static org.junit.Assert.*;
public class BottleTest {

    @Test
    public void getID() {
        Bottle bottle = new Bottle(1, "Water Bottle", 500);
        assertEquals(bottle.getID(),1);
    }

    @Test
    public void getName() {
        Bottle bottle = new Bottle(1, "Water Bottle", 500);
        assertEquals(bottle.getName(),"Water Bottle");
    }

    @Test
    public void getCapacity() {
        Bottle bottle = new Bottle(1, "Water Bottle", 500);
        assertEquals(bottle.getCapacity() ,500);
    }

    @Test
    public void setCapacity() {
        Bottle bottle = new Bottle(1, "Water Bottle", 500);
        bottle.setCapacity(600);
        assertEquals(bottle.getCapacity() , 600);
    }
}