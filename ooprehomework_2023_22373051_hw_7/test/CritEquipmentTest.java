import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CritEquipmentTest {

    CritEquipment critEquipment = new CritEquipment(1, "l", 2, 3, "CritEquipment", 4);

    @Test
    public void getName() {
        assertEquals(critEquipment.getName(), "l");
    }

    @Test
    public void getStar() {
        assertEquals(critEquipment.getStar(), 2);
    }

    @Test
    public void getMoreStar() {
        critEquipment.getMoreStar();
        assertEquals(critEquipment.getStar(), 3);
    }

    @Test
    public void getId() {
        assertEquals(critEquipment.getId(), 1);
    }

    @Test
    public void getPrice() {
        assertEquals(critEquipment.getPrice(), 3);
    }

    @Test
    public void getType() {
        assertEquals(critEquipment.getType(), "CritEquipment");
    }

    @Test
    public void getCritical() {
        assertEquals(critEquipment.getCritical(), 4);
    }
}