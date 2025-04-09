import org.junit.Test;
import static org.junit.Assert.*;

public class EquipmentTest {

    @Test
    public void getName() {
        Equipment equipment = new Equipment(1, "k", 2);
        assertEquals(equipment.getName(),"k");
    }

    @Test
    public void getStar() {
        Equipment equipment = new Equipment(1, "k", 2);
        assertEquals(equipment.getStar() , 2);
    }

    @Test
    public void getMoreStar() {
        Equipment equipment = new Equipment(1, "li", 2);
        equipment.getMoreStar();
        assertEquals(equipment.getStar() ,3);
    }

    @Test
    public void trash() {

    }
}