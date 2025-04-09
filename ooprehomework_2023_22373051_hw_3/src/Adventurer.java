import java.util.HashMap;

public class Adventurer {
    private int id;
    private String name;
    private HashMap<Integer, Bottle> bottleMap;
    private HashMap<Integer, Equipment> equipmentMap;
    private HashMap<Integer, Food> foodMap;

    private int level;
    private int hitPoint;

    private Equipment ownEquipment;
    private HashMap<Integer, Bottle> ownBottleMap;
    private HashMap<Integer, Food> ownFoodMap;

    public void getMoreHitPoint(int x) {
        this.hitPoint += x;
    }

    public void getMoreLevel(int x) {
        this.level += x;
    }

    public int getLevel() {
        return level;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public void setOwnEquipment(Equipment ownEquipment) {
        this.ownEquipment = ownEquipment;
    }

    public Equipment getOwnEquipment() {
        return ownEquipment;
    }

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.level = 1;
        this.hitPoint = 500;
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, Bottle> getBottleMap() {
        if (bottleMap == null) {
            HashMap<Integer, Bottle> tmp = new HashMap<>();
            bottleMap = tmp;
            return tmp;
        }
        return bottleMap;
    }

    public HashMap<Integer, Equipment> getEquipmentMap() {
        if (equipmentMap == null) {
            HashMap<Integer, Equipment> tmp = new HashMap<>();
            equipmentMap = tmp;
            return tmp;
        }
        return equipmentMap;
    }

    public HashMap<Integer, Bottle> getOwnBottleMap() {
        if (ownBottleMap == null) {
            HashMap<Integer, Bottle> tmp = new HashMap<>();
            ownBottleMap = tmp;
            return tmp;
        }
        return ownBottleMap;
    }

    public HashMap<Integer, Food> getFoodMap() {
        if (foodMap == null) {
            HashMap<Integer, Food> tmp = new HashMap<>();
            foodMap = tmp;
            return tmp;
        }
        return foodMap;
    }

    public HashMap<Integer, Food> getOwnFoodMap() {
        if (ownFoodMap == null) {
            HashMap<Integer, Food> tmp = new HashMap<>();
            ownFoodMap = tmp;
            return tmp;
        }
        return ownFoodMap;
    }
}
