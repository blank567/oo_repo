import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RegularEquipmentTest {

    RegularEquipment regularEquipment = new RegularEquipment(1, "l", 2, 3, "RegularEquipment");

    @Test
    public void getName() {
        assertEquals(regularEquipment.getName(), "l");
    }

    @Test
    public void getStar() {
        assertEquals(regularEquipment.getStar(), 2);
    }

    @Test
    public void getMoreStar() {
        regularEquipment.getMoreStar();
        assertEquals(regularEquipment.getStar(), 3);
    }

    @Test
    public void getId() {
        assertEquals(regularEquipment.getId(), 1);
    }

    @Test
    public void getPrice() {
        assertEquals(regularEquipment.getPrice(), 3);
    }

    @Test
    public void getType() {
        assertEquals(regularEquipment.getType(), "RegularEquipment");
    }
}