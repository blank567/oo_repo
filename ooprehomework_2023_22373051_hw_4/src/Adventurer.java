import java.util.ArrayList;
import java.util.HashMap;

public class Adventurer {
    private int id;
    private String name;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.level = 1;
        this.hitPoint = 500;
    }

    private HashMap<Integer, Bottle> bottleMap;
    private HashMap<Integer, Equipment> equipmentMap;
    private HashMap<Integer, Food> foodMap;

    private int level;
    private int hitPoint;

    private ArrayList<Equipment> ownEquipment;
    private HashMap<Integer, Bottle> ownBottleMap;
    private HashMap<Integer, Food> ownFoodMap;

    private ArrayList<Attack> attacks;
    private ArrayList<Attacked> attackeds;

    public ArrayList<Equipment> getOwnEquipment() {
        if (ownEquipment == null) {
            ArrayList<Equipment> tmp = new ArrayList<>();
            ownEquipment = tmp;
            return tmp;
        }
        return ownEquipment;
    }

    public ArrayList<Attack> getAttacks() {
        if (attacks == null) {
            ArrayList<Attack> tmp = new ArrayList<>();
            attacks = tmp;
            return tmp;
        }
        return attacks;
    }

    public ArrayList<Attacked> getAttackeds() {
        if (attackeds == null) {
            ArrayList<Attacked> tmp = new ArrayList<>();
            attackeds = tmp;
            return tmp;
        }
        return attackeds;
    }

    public void readAttackArrayList() {
        int flag = 0;
        if (attacks == null) {
            System.out.println("No Matched Log");
            return;
        }
        for (Attack attack : attacks) {
            flag = 1;
            if (attack.getAttackName() == null) {
                System.out.print(attack.getYear() + "/" + attack.getMonth() + " " + this.name);
                System.out.println(" AOE-attacked with " + attack.getEquipmetName());
            } else {
                System.out.print(attack.getYear() + "/" + attack.getMonth() + " " + this.name);
                System.out.print(" attacked ");
                System.out.println(attack.getAttackName() + " with " + attack.getEquipmetName());
            }
        }
        if (flag == 0) {
            System.out.println("No Matched Log");
        }
    }

    public void readAttackedArrayList() {
        int flag = 0;
        if (attackeds == null) {
            System.out.println("No Matched Log");
            return;
        }
        for (Attacked attacked : attackeds) {
            flag = 1;
            if (attacked.getType() == 2) {
                System.out.print(attacked.getYear() + "/" + attacked.getMonth() + " ");
                System.out.print(attacked.getAttackedName());
                System.out.println(" AOE-attacked with " + attacked.getEquipmetName());
            } else {
                System.out.print(attacked.getYear() + "/" + attacked.getMonth() + " ");
                System.out.print(attacked.getAttackedName() + " attacked ");
                System.out.println(this.name + " with " + attacked.getEquipmetName());
            }
        }
        if (flag == 0) {
            System.out.println("No Matched Log");
        }
    }

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

    public String getName() {
        return name;
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
