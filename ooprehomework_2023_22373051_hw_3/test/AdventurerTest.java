import org.junit.Test;
import static org.junit.Assert.*;

public class AdventurerTest {

    @Test
    public void getMoreHitPoint() {
        Adventurer adventurer = new Adventurer(2, "l");
        adventurer.getMoreHitPoint(2);
        assertEquals(adventurer.getHitPoint() , 502);
    }

    @Test
    public void getMoreLevel() {
        Adventurer adventurer = new Adventurer(2, "l");
        adventurer.getMoreLevel(2);
        assertEquals(adventurer.getLevel(),3);
    }

    @Test
    public void getLevel() {
        Adventurer adventurer = new Adventurer(2, "l");
        assertEquals(adventurer.getLevel() , 1);
    }

    @Test
    public void getHitPoint() {
        Adventurer adventurer = new Adventurer(2, "l");
        assertEquals( adventurer.getHitPoint() , 500);
    }

    @Test
    public void setOwnEquipment() {
        Adventurer adventurer = new Adventurer(2, "l");
        Equipment equipment = new Equipment(1, "oo", 2);
        adventurer.setOwnEquipment(equipment);
        assertEquals(adventurer.getOwnEquipment() , equipment);
    }

    @Test
    public void getOwnEquipment() {
        Adventurer adventurer = new Adventurer(2, "l");
        Equipment equipment = new Equipment(1, "oo", 2);
        adventurer.setOwnEquipment(equipment);
        assertEquals(adventurer.getOwnEquipment() , equipment);
    }

    @Test
    public void getId() {
        Adventurer adventurer = new Adventurer(2, "l");
        assertEquals(adventurer.getId() , 2);
    }

    @Test
    public void getBottleMap() {
        Adventurer adventurer = new Adventurer(2, "l");
        Bottle bottle = new Bottle(1, "bot", 2);
        adventurer.getBottleMap().put(1, bottle);
        assertEquals(adventurer.getBottleMap().size() , 1);
    }

    @Test
    public void getEquipmentMap() {
        Adventurer adventurer = new Adventurer(1, "l");
        Equipment equipment = new Equipment(1, "k", 2);
        adventurer.getEquipmentMap().put(1, equipment);
        assertEquals(adventurer.getEquipmentMap().size() , 1);
    }

    @Test
    public void getOwnBottleMap() {
        Adventurer adventurer = new Adventurer(2, "l");
        Bottle bottle = new Bottle(1, "bot", 2);
        adventurer.getOwnBottleMap().put(1, bottle);
        assertEquals(adventurer.getOwnBottleMap().size() ,1);
    }

    @Test
    public void getFoodMap() {
        Adventurer adventurer = new Adventurer(1, "l");
        Food food = new Food(1, "k", 2);
        adventurer.getFoodMap().put(1,food);
        assertEquals(adventurer.getFoodMap().size() , 1);
    }

    @Test
    public void getOwnFoodMap() {
        Adventurer adventurer = new Adventurer(1, "l");
        Food food = new Food(1, "k", 2);
        adventurer.getOwnFoodMap().put(1,food);
        assertEquals(adventurer.getOwnFoodMap().size() ,1);
    }
}