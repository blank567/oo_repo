import org.junit.Test;
import static org.junit.Assert.*;

public class AdventurerTest {

    @Test
    public void getOwnEquipment() {
        Adventurer adventurer = new Adventurer(1, "l");
        Equipment equipment = new Equipment(1, "k", 2);
        adventurer.getOwnEquipment().add(equipment);
        assertEquals(adventurer.getOwnEquipment().get(0), equipment);
    }

    @Test
    public void getAttacks() {
        Adventurer adventurer = new Adventurer(1, "l");
        Attack attack = new Attack("2000", "11", "l");
        adventurer.getAttacks().add(attack);
        assertEquals(adventurer.getAttacks().get(0), attack);

    }

    @Test
    public void getAttackeds() {
        Adventurer adventurer = new Adventurer(1, "l");
        Attacked attacked = new Attacked("2000", "11", "l","k",1);
        adventurer.getAttackeds().add(attacked);
        assertEquals(adventurer.getAttackeds().get(0), attacked);
    }

    @Test
    public void readAttackArrayList() {
        Adventurer adventurer = new Adventurer(1, "l");
        adventurer.readAttackArrayList();
        Attack attack1 = new Attack("2000", "10", "Sword");
        Attack attack2 = new Attack("2000", "11", "k", "p");
        adventurer.getAttacks();
        adventurer.readAttackArrayList();
        adventurer.getAttacks().add(attack1);
        adventurer.readAttackArrayList();
        adventurer.getAttacks().add(attack2);
        adventurer.readAttackArrayList();
    }

    @Test
    public void readAttackedArrayList() {
        Adventurer adventurer=new Adventurer(1,"l");
        adventurer.readAttackedArrayList();
        Attacked attacked1=new Attacked("2000","12","l","k",1);
        Attacked attacked2=new Attacked("2000","12","l","k",2);
        adventurer.getAttackeds();
        adventurer.readAttackedArrayList();
        adventurer.getAttackeds().add(attacked1);
        adventurer.readAttackedArrayList();
        adventurer.getAttackeds().add(attacked2);
        adventurer.readAttackedArrayList();
    }

    @Test
    public void getMoreHitPoint() {
        Adventurer adventurer = new Adventurer(2, "l");
        adventurer.getMoreHitPoint(2);
        assertEquals(adventurer.getHitPoint(), 502);
    }

    @Test
    public void getMoreLevel() {
        Adventurer adventurer = new Adventurer(2, "l");
        adventurer.getMoreLevel(2);
        assertEquals(adventurer.getLevel(), 3);
    }

    @Test
    public void getLevel() {
        Adventurer adventurer = new Adventurer(2, "l");
        assertEquals(adventurer.getLevel(), 1);
    }

    @Test
    public void getHitPoint() {
        Adventurer adventurer = new Adventurer(2, "l");
        assertEquals(adventurer.getHitPoint(), 500);
    }

    @Test
    public void getName() {
        Adventurer adventurer = new Adventurer(2, "l");
        assertEquals(adventurer.getName(), "l");
    }

    @Test
    public void getId() {
        Adventurer adventurer = new Adventurer(2, "l");
        assertEquals(adventurer.getId(), 2);
    }

    @Test
    public void getBottleMap() {
        Adventurer adventurer = new Adventurer(2, "l");
        Bottle bottle = new Bottle(1, "bot", 2);
        adventurer.getBottleMap().put(1, bottle);
        assertEquals(adventurer.getBottleMap().size(), 1);
    }

    @Test
    public void getEquipmentMap() {
        Adventurer adventurer = new Adventurer(1, "l");
        Equipment equipment = new Equipment(1, "k", 2);
        adventurer.getEquipmentMap().put(1, equipment);
        assertEquals(adventurer.getEquipmentMap().size(), 1);
    }

    @Test
    public void getOwnBottleMap() {
        Adventurer adventurer = new Adventurer(2, "l");
        Bottle bottle = new Bottle(1, "bot", 2);
        adventurer.getOwnBottleMap().put(1, bottle);
        assertEquals(adventurer.getOwnBottleMap().size(), 1);
    }

    @Test
    public void getFoodMap() {
        Adventurer adventurer = new Adventurer(1, "l");
        Food food = new Food(1, "k", 2);
        adventurer.getFoodMap().put(1, food);
        assertEquals(adventurer.getFoodMap().size(), 1);
    }

    @Test
    public void getOwnFoodMap() {
        Adventurer adventurer = new Adventurer(1, "l");
        Food food = new Food(1, "k", 2);
        adventurer.getOwnFoodMap().put(1, food);
        assertEquals(adventurer.getOwnFoodMap().size(), 1);
    }
}