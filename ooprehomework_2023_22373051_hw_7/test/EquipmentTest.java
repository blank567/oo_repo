import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EquipmentTest {

    Equipment equipment = new Equipment(1, "k", 2);

    @Test
    public void getName() {
        assertEquals(equipment.getName(), "k");
    }

    @Test
    public void getStar() {
        assertEquals(equipment.getStar(), 2);
    }

    @Test
    public void getMoreStar() {
        equipment.getMoreStar();
        assertEquals(equipment.getStar(), 3);
    }

    @Test
    public void getId() {
        assertEquals(equipment.getId(), 1);
    }
}