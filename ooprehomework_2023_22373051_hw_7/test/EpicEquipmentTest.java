import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class EpicEquipmentTest {

    EpicEquipment epicEquipment = new EpicEquipment(1, "l", 2, 3, "EpicEquipment", 3.0);

    @Test
    public void getName() {
        assertEquals(epicEquipment.getName(), "l");
    }

    @Test
    public void getStar() {
        assertEquals(epicEquipment.getStar(), 2);
    }

    @Test
    public void getMoreStar() {
        epicEquipment.getMoreStar();
        assertEquals(epicEquipment.getStar(), 3);
    }

    @Test
    public void getId() {
        assertEquals(epicEquipment.getId(), 1);
    }

    @Test
    public void getPrice() {
        assertEquals(epicEquipment.getPrice(), 3);
    }

    @Test
    public void getType() {
        assertEquals(epicEquipment.getType(), "EpicEquipment");
    }

    @Test
    public void getRatio() {
        assertEquals(epicEquipment.getRatio(), 3.0, 1e-8);
    }
}