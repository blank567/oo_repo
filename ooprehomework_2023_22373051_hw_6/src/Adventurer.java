import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Adventurer {
    private final int id;
    private final String name;
    private int level;
    private int hitPoint;

    private final HashMap<Integer, Bottle> bottleMap;
    private final HashMap<Integer, Equipment> equipmentMap;
    private final HashMap<Integer, Food> foodMap;

    private final ArrayList<Equipment> ownEquipment;
    private final HashMap<Integer, Bottle> ownBottleMap;
    private final HashMap<Integer, Food> ownFoodMap;

    private final ArrayList<Attack> attacks;
    private final ArrayList<Attacked> attackeds;

    private final ArrayList<Adventurer> employees;

    private final HashMap<Integer, RegularBottle> regularBottleMap;
    private final HashMap<Integer, RecoverBottle> recoverBottleMap;
    private final HashMap<Integer, ReinforcedBottle> reinforcedBottleMap;

    private final HashMap<Integer, RegularEquipment> regularEquipmentMap;
    private final HashMap<Integer, EpicEquipment> epicEquipmentMap;
    private final HashMap<Integer, CritEquipment> critEquipmentMap;

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
        this.level = 1;
        this.hitPoint = 500;

        this.bottleMap = new HashMap<>();
        this.equipmentMap = new HashMap<>();
        this.foodMap = new HashMap<>();

        this.ownEquipment = new ArrayList<>();
        this.ownBottleMap = new HashMap<>();
        this.ownFoodMap = new HashMap<>();

        this.attacks = new ArrayList<>();
        this.attackeds = new ArrayList<>();

        this.employees = new ArrayList<>();

        this.regularBottleMap = new HashMap<>();
        this.recoverBottleMap = new HashMap<>();
        this.reinforcedBottleMap = new HashMap<>();

        this.regularEquipmentMap = new HashMap<>();
        this.epicEquipmentMap = new HashMap<>();
        this.critEquipmentMap = new HashMap<>();
    }

    public HashMap<Integer, Bottle> getBottleMap() {
        return bottleMap;
    }

    public HashMap<Integer, Equipment> getEquipmentMap() {
        return equipmentMap;
    }

    public HashMap<Integer, Food> getFoodMap() {
        return foodMap;
    }

    public ArrayList<Equipment> getOwnEquipment() {
        return ownEquipment;
    }

    public HashMap<Integer, Bottle> getOwnBottleMap() {
        return ownBottleMap;
    }

    public HashMap<Integer, Food> getOwnFoodMap() {
        return ownFoodMap;
    }

    public ArrayList<Attack> getAttacks() {
        return attacks;
    }

    public ArrayList<Attacked> getAttackeds() {
        return attackeds;
    }

    public ArrayList<Adventurer> getEmployees() {
        return employees;
    }

    public HashMap<Integer, RegularBottle> getRegularBottleMap() {
        return regularBottleMap;
    }

    public HashMap<Integer, RecoverBottle> getRecoverBottleMap() {
        return recoverBottleMap;
    }

    public HashMap<Integer, ReinforcedBottle> getReinforcedBottleMap() {
        return reinforcedBottleMap;
    }

    public HashMap<Integer, RegularEquipment> getRegularEquipmentMap() {
        return regularEquipmentMap;
    }

    public HashMap<Integer, EpicEquipment> getEpicEquipmentMap() {
        return epicEquipmentMap;
    }

    public HashMap<Integer, CritEquipment> getCritEquipmentMap() {
        return critEquipmentMap;
    }

    public void readAttackArrayList() {
        int flag = 0;
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

    public boolean useOwnBottle(String name) {
        TreeMap<Integer, Bottle> sortedMap = new TreeMap<>(ownBottleMap);
        for (Map.Entry<Integer, Bottle> entry : sortedMap.entrySet()) {
            Bottle tmp = entry.getValue();
            if (tmp.getName().equals(name)) {
                String type = findInWhichBottle(tmp.getID());
                switch (type) {
                    case "RegularBottle":
                        if (regularBottleMap.containsKey(tmp.getID())) {
                            RegularBottle regularBottle = regularBottleMap.get(tmp.getID());
                            if (regularBottle.getCapacity() == 0) {
                                bottleMap.remove(tmp.getID());
                                ownBottleMap.remove(tmp.getID());
                                regularBottleMap.remove(tmp.getID());
                            } else {
                                hitPoint += regularBottle.getCapacity();
                                ownBottleMap.get(tmp.getID()).setCapacity(0);
                                bottleMap.get(tmp.getID()).setCapacity(0);
                                regularBottleMap.get(tmp.getID()).setCapacity(0);
                            }
                        }
                        break;
                    case "ReinforcedBottle":
                        if (reinforcedBottleMap.containsKey(tmp.getID())) {
                            ReinforcedBottle bo = reinforcedBottleMap.get(tmp.getID());
                            if (bo.getCapacity() == 0) {
                                bottleMap.remove(tmp.getID());
                                ownBottleMap.remove(tmp.getID());
                                reinforcedBottleMap.remove(tmp.getID());
                            } else {
                                hitPoint += (bo.getCapacity() * (bo.getRatio() + 1));
                                ownBottleMap.get(tmp.getID()).setCapacity(0);
                                bottleMap.get(tmp.getID()).setCapacity(0);
                                reinforcedBottleMap.get(tmp.getID()).setCapacity(0);
                            }
                        }
                        break;
                    case "RecoverBottle":
                        if (recoverBottleMap.containsKey(tmp.getID())) {
                            RecoverBottle bo1 = recoverBottleMap.get(tmp.getID());
                            if (bo1.getCapacity() == 0) {
                                bottleMap.remove(tmp.getID());
                                ownBottleMap.remove(tmp.getID());
                                recoverBottleMap.remove(tmp.getID());
                            } else {
                                hitPoint += (hitPoint * bo1.getRatio());
                                ownBottleMap.get(tmp.getID()).setCapacity(0);
                                bottleMap.get(tmp.getID()).setCapacity(0);
                                recoverBottleMap.get(tmp.getID()).setCapacity(0);
                            }
                        }
                        break;
                    default:
                }
                System.out.println(tmp.getID() + " " + hitPoint);
                return true;
            }
        }
        return false;
    }

    public String findInWhichBottle(int id) {
        if (regularBottleMap.containsKey(id)) {
            return regularBottleMap.get(id).getType();
        } else if (recoverBottleMap.containsKey(id)) {
            return recoverBottleMap.get(id).getType();
        } else if (reinforcedBottleMap.containsKey(id)) {
            return reinforcedBottleMap.get(id).getType();
        }
        return null;
    }

    public String findInWhichEquipment(int id) {
        if (regularEquipmentMap.containsKey(id)) {
            return regularEquipmentMap.get(id).getType();
        } else if (epicEquipmentMap.containsKey(id)) {
            return epicEquipmentMap.get(id).getType();
        } else if (critEquipmentMap.containsKey(id)) {
            return critEquipmentMap.get(id).getType();
        }
        return null;
    }

    public void removeBottle(int id) {
        Bottle tmp = bottleMap.remove(id);
        ownBottleMap.remove(id);
        System.out.println(bottleMap.size() + " " + tmp.getName());

        String type = findInWhichBottle(id);
        switch (type) {
            case "RegularBottle":
                regularBottleMap.remove(id);
                break;
            case "ReinforcedBottle":
                reinforcedBottleMap.remove(id);
                break;
            case "RecoverBottle":
                recoverBottleMap.remove(id);
                break;
            default:
        }
    }

    public void removeEquipment(int id) {
        Equipment tmp = equipmentMap.remove(id);
        for (Equipment equipment : ownEquipment) {
            if (equipment.getId() == id) {
                ownEquipment.remove(equipment);
                break;
            }
        }
        System.out.println(equipmentMap.size() + " " + tmp.getName());

        String type = findInWhichEquipment(id);
        switch (type) {
            case "RegularEquipment":
                regularEquipmentMap.remove(id);
                break;
            case "CritEquipment":
                critEquipmentMap.remove(id);
                break;
            case "EpicEquipment":
                epicEquipmentMap.remove(id);
                break;
            default:
        }
    }

    public void addEquipmentStar(int id) {
        Equipment tmp = equipmentMap.get(id);
        tmp.getMoreStar();
        System.out.println(tmp.getName() + " " + tmp.getStar());

        String type = findInWhichEquipment(id);
        switch (type) {
            case "RegularEquipment":
                regularEquipmentMap.get(id).getMoreStar();
                break;
            case "CritEquipment":
                critEquipmentMap.get(id).getMoreStar();
                break;
            case "EpicEquipment":
                epicEquipmentMap.get(id).getMoreStar();
                break;
            default:
        }
    }

    public void equipmentAttack(String type, Adventurer adventurer, int equipmentId) {
        int attackPower;
        switch (type) {
            case "RegularEquipment":
                RegularEquipment regularEquipment = regularEquipmentMap.get(equipmentId);
                attackPower = level * regularEquipment.getStar();
                adventurer.getMoreHitPoint(-attackPower);
                break;
            case "CritEquipment":
                CritEquipment critEquipment = critEquipmentMap.get(equipmentId);
                attackPower = level * critEquipment.getStar() + critEquipment.getCritical();
                adventurer.getMoreHitPoint(-attackPower);
                break;
            case "EpicEquipment":
                EpicEquipment epicEquipment = epicEquipmentMap.get(equipmentId);
                attackPower = (int) (adventurer.getHitPoint() * epicEquipment.getRatio());
                adventurer.getMoreHitPoint(-attackPower);
                break;
            default:
        }
    }

    public int count() {
        return bottleMap.size() + employees.size() + equipmentMap.size() + foodMap.size();
    }

    public long bottlePrice() {
        long sum = 0;
        for (Map.Entry<Integer, RegularBottle> entry : regularBottleMap.entrySet()) {
            RegularBottle value = entry.getValue();
            sum += value.getPrice();
        }
        for (Map.Entry<Integer, RecoverBottle> entry : recoverBottleMap.entrySet()) {
            RecoverBottle value = entry.getValue();
            sum += value.getPrice();
        }
        for (Map.Entry<Integer, ReinforcedBottle> entry : reinforcedBottleMap.entrySet()) {
            ReinforcedBottle value = entry.getValue();
            sum += value.getPrice();
        }
        return sum;
    }

    public long maxBottlePrice() {
        long max = 0;
        for (Map.Entry<Integer, RegularBottle> entry : regularBottleMap.entrySet()) {
            RegularBottle value = entry.getValue();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, RecoverBottle> entry : recoverBottleMap.entrySet()) {
            RecoverBottle value = entry.getValue();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, ReinforcedBottle> entry : reinforcedBottleMap.entrySet()) {
            ReinforcedBottle value = entry.getValue();
            max = Math.max(value.getPrice(), max);
        }
        return max;
    }

    public long equipmentPrice() {
        long sum = 0;
        for (Map.Entry<Integer, RegularEquipment> entry : regularEquipmentMap.entrySet()) {
            RegularEquipment value = entry.getValue();
            sum += value.getPrice();
        }
        for (Map.Entry<Integer, EpicEquipment> entry : epicEquipmentMap.entrySet()) {
            EpicEquipment value = entry.getValue();
            sum += value.getPrice();
        }
        for (Map.Entry<Integer, CritEquipment> entry : critEquipmentMap.entrySet()) {
            CritEquipment value = entry.getValue();
            sum += value.getPrice();
        }
        return sum;
    }

    public long maxEquipmentPrice() {
        long max = 0;
        for (Map.Entry<Integer, RegularEquipment> entry : regularEquipmentMap.entrySet()) {
            RegularEquipment value = entry.getValue();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, EpicEquipment> entry : epicEquipmentMap.entrySet()) {
            EpicEquipment value = entry.getValue();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, CritEquipment> entry : critEquipmentMap.entrySet()) {
            CritEquipment value = entry.getValue();
            max = Math.max(value.getPrice(), max);
        }
        return max;
    }

    public long foodPrice() {
        long sum = 0;
        for (Integer key : foodMap.keySet()) {
            Food value = foodMap.get(key);
            sum += value.getPrice();
        }
        return sum;
    }

    public long maxFoodPrice() {
        long max = 0;
        for (Integer key : foodMap.keySet()) {
            Food value = foodMap.get(key);
            max = Math.max(value.getPrice(), max);
        }
        return max;
    }

    public long sumOfAllPrice() {
        long totalSum = bottlePrice() + equipmentPrice() + foodPrice();
        if (!employees.isEmpty()) {
            for (Adventurer adventurer : employees) {
                totalSum += adventurer.sumOfAllPrice();
            }
        }
        return totalSum;
    }

    public long getMaxPrice() {
        long max = Math.max(maxBottlePrice(), Math.max(maxEquipmentPrice(), maxFoodPrice()));
        for (Adventurer adventurer : employees) {
            max = Math.max(max, adventurer.sumOfAllPrice());
        }
        return max;
    }

    public void findInWhichClass(int comId) {
        String com;
        if (findInWhichBottle(comId) != null) {
            com = findInWhichBottle(comId);
        } else if (findInWhichEquipment(comId) != null) {
            com = findInWhichEquipment(comId);
        } else if (foodMap.containsKey(comId)) {
            com = "Food";
        } else {
            com = "Adventurer";
        }
        System.out.println("Commodity whose id is " + comId + " belongs to " + com);
    }
}
