import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AdventurerTest {
    Adventurer adventurer = new Adventurer(1, "l");
    Food food = new Food(1, "k", 2, 3);
    Bottle bottle = new Bottle(1, "bottle1", 2);
    Equipment equipment = new Equipment(1, "m", 2);
    RegularBottle regularBottle = new RegularBottle(1, "l", 2, 3, "RegularBottle");
    RecoverBottle recoverBottle = new RecoverBottle(1, "l", 2, 3, "RecoverBottle", 4.0);
    ReinforcedBottle reinforcedBottle = new ReinforcedBottle(1, "l", 2, 3, "ReinforcedBottle", 2.0);
    RegularEquipment regularEquipment = new RegularEquipment(1, "l", 2, 3, "RegularEquipment");
    EpicEquipment epicEquipment = new EpicEquipment(1, "l", 2, 3, "EpicEquipment", 3.0);
    CritEquipment critEquipment = new CritEquipment(1, "l", 2, 3, "CritEquipment", 4);

    @Before
    public void method() {
        adventurer.getRegularBottleMap().put(1, regularBottle);
        adventurer.getReinforcedBottleMap().put(1, reinforcedBottle);
        adventurer.getRecoverBottleMap().put(1, recoverBottle);

        adventurer.getRegularEquipmentMap().put(1, regularEquipment);
        adventurer.getCritEquipmentMap().put(1, critEquipment);
        adventurer.getEpicEquipmentMap().put(1, epicEquipment);

        adventurer.getFoodMap().put(1, food);

        Adventurer adventurer1 = new Adventurer(1, "k");
        adventurer.getEmployees().add(adventurer1);
    }

    @Test
    public void getBottleMap() {
        adventurer.getBottleMap().put(1, bottle);
        assertEquals(bottle, adventurer.getBottleMap().get(1));
    }

    @Test
    public void getEquipmentMap() {
        adventurer.getEquipmentMap().put(1, equipment);
        assertEquals(adventurer.getEquipmentMap().get(1), equipment);
    }

    @Test
    public void getFoodMap() {
        adventurer.getFoodMap().put(1, food);
        assertEquals(adventurer.getFoodMap().get(1), food);
    }

    @Test
    public void getOwnEquipment() {
        adventurer.getOwnEquipment().add(equipment);
        assertEquals(adventurer.getOwnEquipment().get(0), equipment);
    }

    @Test
    public void getOwnBottleMap() {
        adventurer.getOwnBottleMap().put(1, bottle);
        assertEquals(adventurer.getOwnBottleMap().size(), 1);
    }

    @Test
    public void getOwnFoodMap() {
        adventurer.getOwnFoodMap().put(1, food);
        assertEquals(adventurer.getOwnFoodMap().size(), 1);
    }

    @Test
    public void getAttacks() {
        Attack attack = new Attack("2000", "11", "l");
        adventurer.getAttacks().add(attack);
        assertEquals(adventurer.getAttacks().get(0), attack);
    }

    @Test
    public void getAttackeds() {
        Attacked attacked = new Attacked("2000", "11", "l", "k", 1);
        adventurer.getAttackeds().add(attacked);
        assertEquals(adventurer.getAttackeds().get(0), attacked);
    }

    @Test
    public void getEmployees() {
        Adventurer adventurer2 = new Adventurer(1, "k");
        adventurer.getEmployees().add(adventurer2);
        assertEquals(adventurer.getEmployees().get(1), adventurer2);
    }

    @Test
    public void getRegularBottleMap() {
        assertEquals(adventurer.getRegularBottleMap().get(1), regularBottle);
    }

    @Test
    public void getRecoverBottleMap() {
        assertEquals(adventurer.getRecoverBottleMap().get(1), recoverBottle);
    }

    @Test
    public void getReinforcedBottleMap() {
        assertEquals(adventurer.getReinforcedBottleMap().get(1), reinforcedBottle);
    }

    @Test
    public void getRegularEquipmentMap() {
        assertEquals(adventurer.getRegularEquipmentMap().get(1), regularEquipment);
    }

    @Test
    public void getEpicEquipmentMap() {
        assertEquals(adventurer.getEpicEquipmentMap().get(1), epicEquipment);
    }

    @Test
    public void getCritEquipmentMap() {
        assertEquals(adventurer.getCritEquipmentMap().get(1), critEquipment);
    }

    @Test
    public void readAttackArrayList() {
        adventurer.readAttackArrayList();
        Attack attack1 = new Attack("2000", "10", "Sword");
        Attack attack2 = new Attack("2000", "11", "k", "p");
        adventurer.readAttackArrayList();
        adventurer.getAttacks().add(attack1);
        adventurer.readAttackArrayList();
        adventurer.getAttacks().add(attack2);
        adventurer.readAttackArrayList();
    }

    @Test
    public void readAttackedArrayList() {
        adventurer.readAttackedArrayList();
        Attacked attacked1 = new Attacked("2000", "12", "l", "k", 1);
        Attacked attacked2 = new Attacked("2000", "12", "l", "k", 2);
        adventurer.readAttackedArrayList();
        adventurer.getAttackeds().add(attacked1);
        adventurer.readAttackedArrayList();
        adventurer.getAttackeds().add(attacked2);
        adventurer.readAttackedArrayList();
    }

    @Test
    public void getMoreHitPoint() {
        adventurer.getMoreHitPoint(2);
        assertEquals(adventurer.getHitPoint(), 502);
    }

    @Test
    public void getMoreLevel() {
        adventurer.getMoreLevel(2);
        assertEquals(adventurer.getLevel(), 3);
    }

    @Test
    public void getLevel() {
        assertEquals(adventurer.getLevel(), 1);
    }

    @Test
    public void getHitPoint() {
        assertEquals(adventurer.getHitPoint(), 500);
    }

    @Test
    public void getName() {
        assertEquals(adventurer.getName(), "l");
    }

    @Test
    public void getId() {
        assertEquals(adventurer.getId(), 1);
    }

    @Test
    public void useOwnBottle() {
        RegularBottle regularBottle = new RegularBottle(2, "m", 2, 3, "RegularBottle");
        ReinforcedBottle reinforcedBottle = new ReinforcedBottle(3, "j", 2, 3, "ReinforcedBottle", 2.0);
        RecoverBottle recoverBottle = new RecoverBottle(4, "n", 2, 3, "RecoverBottle", 4.0);
        adventurer.getRegularBottleMap().put(2, regularBottle);
        adventurer.getReinforcedBottleMap().put(3, reinforcedBottle);
        adventurer.getRecoverBottleMap().put(4, recoverBottle);

        adventurer.getBottleMap().put(2, regularBottle);
        adventurer.getBottleMap().put(3, reinforcedBottle);
        adventurer.getBottleMap().put(4, recoverBottle);

        adventurer.getOwnBottleMap().put(2, regularBottle);
        adventurer.getOwnBottleMap().put(3, reinforcedBottle);
        adventurer.getOwnBottleMap().put(4, recoverBottle);

        adventurer.useOwnBottle("m");
        assertEquals(regularBottle.getCapacity(), 0);
        adventurer.useOwnBottle("n");
        assertEquals(recoverBottle.getCapacity(), 0);
        adventurer.useOwnBottle("j");
        assertEquals(reinforcedBottle.getCapacity(), 0);

        adventurer.useOwnBottle("m");
        adventurer.useOwnBottle("n");
        adventurer.useOwnBottle("j");
        adventurer.useOwnBottle("l");
    }

    @Test
    public void findInWhichBottle() {
        adventurer.getRegularBottleMap().put(2, regularBottle);
        adventurer.getReinforcedBottleMap().put(3, reinforcedBottle);
        adventurer.getRecoverBottleMap().put(4, recoverBottle);

        assertEquals(adventurer.findInWhichBottle(2), "RegularBottle");
        assertEquals(adventurer.findInWhichBottle(3), "ReinforcedBottle");
        assertEquals(adventurer.findInWhichBottle(4), "RecoverBottle");
        assertNull(adventurer.findInWhichBottle(5));
    }

    @Test
    public void findInWhichEquipment() {
        adventurer.getRegularEquipmentMap().put(2, regularEquipment);
        adventurer.getCritEquipmentMap().put(3, critEquipment);
        adventurer.getEpicEquipmentMap().put(4, epicEquipment);

        assertEquals(adventurer.findInWhichEquipment(2), "RegularEquipment");
        assertEquals(adventurer.findInWhichEquipment(3), "CritEquipment");
        assertEquals(adventurer.findInWhichEquipment(4), "EpicEquipment");
        assertNull(adventurer.findInWhichEquipment(5));
    }

    @Test
    public void removeBottle() {
        adventurer.getRegularBottleMap().put(2, regularBottle);
        adventurer.getReinforcedBottleMap().put(3, reinforcedBottle);
        adventurer.getRecoverBottleMap().put(4, recoverBottle);

        adventurer.getBottleMap().put(2, regularBottle);
        adventurer.getBottleMap().put(3, reinforcedBottle);
        adventurer.getBottleMap().put(4, recoverBottle);

        adventurer.removeBottle(2);
        adventurer.removeBottle(3);
        adventurer.removeBottle(4);

    }

    @Test
    public void removeEquipment() {
        adventurer.getRegularEquipmentMap().put(5, regularEquipment);
        adventurer.getCritEquipmentMap().put(6, critEquipment);
        adventurer.getEpicEquipmentMap().put(7, epicEquipment);

        adventurer.getEquipmentMap().put(5, regularEquipment);
        adventurer.getEquipmentMap().put(6, critEquipment);
        adventurer.getEquipmentMap().put(7, epicEquipment);

        Equipment equipment = new Equipment(5, "l", 1);
        Equipment equipment1 = new Equipment(2, "l", 1);
        adventurer.getOwnEquipment().add(equipment);
        adventurer.getOwnEquipment().add(equipment1);

        adventurer.removeEquipment(5);
        adventurer.removeEquipment(6);
        adventurer.removeEquipment(7);
    }

    @Test
    public void addEquipmentStar() {
        adventurer.getRegularEquipmentMap().put(5, regularEquipment);
        adventurer.getCritEquipmentMap().put(6, critEquipment);
        adventurer.getEpicEquipmentMap().put(7, epicEquipment);

        adventurer.getEquipmentMap().put(5, regularEquipment);
        adventurer.getEquipmentMap().put(6, critEquipment);
        adventurer.getEquipmentMap().put(7, epicEquipment);

        adventurer.addEquipmentStar(5);
        adventurer.addEquipmentStar(6);
        adventurer.addEquipmentStar(7);
    }

    @Test
    public void equipmentAttack() {
        adventurer.getRegularEquipmentMap().put(5, regularEquipment);
        adventurer.getCritEquipmentMap().put(6, critEquipment);
        adventurer.getEpicEquipmentMap().put(7, epicEquipment);

        adventurer.equipmentAttack("RegularEquipment", adventurer, 5);
        assertEquals(adventurer.getHitPoint(), 498);
        adventurer.equipmentAttack("CritEquipment", adventurer, 6);
        assertEquals(adventurer.getHitPoint(), 492);
        adventurer.equipmentAttack("EpicEquipment", adventurer, 7);
        assertEquals(adventurer.getHitPoint(), -984);
    }

    @Test
    public void count() {
        adventurer.getFoodMap().put(1, food);
        adventurer.getBottleMap().put(1, bottle);
        adventurer.getEquipmentMap().put(1, epicEquipment);
        assertEquals(adventurer.count(), 4);
    }

    @Test
    public void bottlePrice() {
        assertEquals(adventurer.bottlePrice(), 9);
    }

    @Test
    public void maxBottlePrice() {
        assertEquals(adventurer.maxBottlePrice(), 3);
    }

    @Test
    public void equipmentPrice() {
        assertEquals(adventurer.equipmentPrice(), 9);
    }

    @Test
    public void maxEquipmentPrice() {
        assertEquals(adventurer.maxEquipmentPrice(), 3);
    }

    @Test
    public void foodPrice() {
        assertEquals(adventurer.foodPrice(), 3);
    }

    @Test
    public void maxFoodPrice() {
        adventurer.getFoodMap().put(1, food);
        assertEquals(adventurer.maxFoodPrice(), 3);
    }

    @Test
    public void sumOfAllPrice() {
        assertEquals(adventurer.sumOfAllPrice(), 21);
    }

    @Test
    public void getMaxPrice() {
        assertEquals(adventurer.getMaxPrice(), 3);
    }

    @Test
    public void findInWhichClass() {
        adventurer.getRegularBottleMap().put(2, regularBottle);
        adventurer.getReinforcedBottleMap().put(3, reinforcedBottle);
        adventurer.getRecoverBottleMap().put(4, recoverBottle);

        adventurer.getRegularEquipmentMap().put(5, regularEquipment);
        adventurer.getCritEquipmentMap().put(6, critEquipment);
        adventurer.getEpicEquipmentMap().put(7, epicEquipment);

        adventurer.getFoodMap().put(8, food);
        adventurer.findInWhichClass(2);
        adventurer.findInWhichClass(5);
        adventurer.findInWhichClass(8);
        adventurer.findInWhichClass(9);
    }
}