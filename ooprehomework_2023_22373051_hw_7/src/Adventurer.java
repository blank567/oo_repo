import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Adventurer {
    private final int id;
    private final String name;
    private int level = 1;
    private int hitPoint = 500;
    private long money = 0;
    private final HashMap<Integer, Bottle> bottleMap = new HashMap<>();
    private final HashMap<Integer, Equipment> equipmentMap = new HashMap<>();
    private final HashMap<Integer, Food> foodMap = new HashMap<>();
    private final ArrayList<Equipment> ownEquipment = new ArrayList<>();
    private final HashMap<Integer, Bottle> ownBottleMap = new HashMap<>();
    private final HashMap<Integer, Food> ownFoodMap = new HashMap<>();
    private final ArrayList<Attack> attacks = new ArrayList<>();
    private final ArrayList<Attacked> attackeds = new ArrayList<>();
    private final ArrayList<Adventurer> employees = new ArrayList<>();
    private final HashMap<Integer, RegularBottle> regularBottleMap = new HashMap<>();
    private final HashMap<Integer, RecoverBottle> recoverBottleMap = new HashMap<>();
    private final HashMap<Integer, ReinforcedBottle> reinforcedBottleMap = new HashMap<>();
    private final HashMap<Integer, RegularEquipment> regularEquipmentMap = new HashMap<>();
    private final HashMap<Integer, EpicEquipment> epicEquipmentMap = new HashMap<>();
    private final HashMap<Integer, CritEquipment> critEquipmentMap = new HashMap<>();

    public Adventurer(int id, String name) {
        this.id = id;
        this.name = name;
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
        for (Attack attack : attacks) {
            System.out.print(attack.getYear() + "/" + attack.getMonth() + " " + this.name);
            if (attack.getAttackName() == null) {
                System.out.print(" AOE-attacked");
            } else {
                System.out.print(" attacked " + attack.getAttackName());
            }
            System.out.println(" with " + attack.getEquipmetName());
        }

        if (attacks.isEmpty()) {
            System.out.println("No Matched Log");
        }
    }

    public void readAttackedArrayList() {
        for (Attacked attacked : attackeds) {
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
        if (attackeds.isEmpty()) {
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

    public long getMoney() {
        return money;
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
                            if (regularBottle.isEmpty()) {
                                bottleMap.remove(tmp.getID());
                                ownBottleMap.remove(tmp.getID());
                                regularBottleMap.remove(tmp.getID());
                            } else {
                                hitPoint += regularBottle.getCapacity();
                                regularBottleMap.get(tmp.getID()).setEmpty(true);
                            }
                        }
                        break;
                    case "ReinforcedBottle":
                        if (reinforcedBottleMap.containsKey(tmp.getID())) {
                            ReinforcedBottle bo = reinforcedBottleMap.get(tmp.getID());
                            if (bo.isEmpty()) {
                                bottleMap.remove(tmp.getID());
                                ownBottleMap.remove(tmp.getID());
                                reinforcedBottleMap.remove(tmp.getID());
                            } else {
                                hitPoint += (bo.getCapacity() * (bo.getRatio() + 1));
                                reinforcedBottleMap.get(tmp.getID()).setEmpty(true);
                            }
                        }
                        break;
                    case "RecoverBottle":
                        if (recoverBottleMap.containsKey(tmp.getID())) {
                            RecoverBottle bo1 = recoverBottleMap.get(tmp.getID());
                            if (bo1.isEmpty()) {
                                bottleMap.remove(tmp.getID());
                                ownBottleMap.remove(tmp.getID());
                                recoverBottleMap.remove(tmp.getID());
                            } else {
                                hitPoint += (hitPoint * bo1.getRatio());
                                recoverBottleMap.get(tmp.getID()).setEmpty(true);
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
        removeBottleMap(id);
    }

    public void removeBottleMap(int id) {
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
        removeEquipmentMap(id);
    }

    public void removeEquipmentMap(int id) {
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
        switch (findInWhichEquipment(id)) {
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

    public ArrayList<Long> bottlePrice() {
        long sum = 0;
        long max = 0;
        for (Map.Entry<Integer, RegularBottle> entry : regularBottleMap.entrySet()) {
            RegularBottle value = entry.getValue();
            sum += value.getPrice();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, RecoverBottle> entry : recoverBottleMap.entrySet()) {
            RecoverBottle value = entry.getValue();
            sum += value.getPrice();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, ReinforcedBottle> entry : reinforcedBottleMap.entrySet()) {
            ReinforcedBottle value = entry.getValue();
            sum += value.getPrice();
            max = Math.max(value.getPrice(), max);
        }
        return new ArrayList<>(Arrays.asList(sum, max));
    }

    public ArrayList<Long> equPrice() {
        long sum = 0;
        long max = 0;
        for (Map.Entry<Integer, RegularEquipment> entry : regularEquipmentMap.entrySet()) {
            RegularEquipment value = entry.getValue();
            sum += value.getPrice();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, EpicEquipment> entry : epicEquipmentMap.entrySet()) {
            EpicEquipment value = entry.getValue();
            sum += value.getPrice();
            max = Math.max(value.getPrice(), max);
        }
        for (Map.Entry<Integer, CritEquipment> entry : critEquipmentMap.entrySet()) {
            CritEquipment value = entry.getValue();
            sum += value.getPrice();
            max = Math.max(value.getPrice(), max);
        }
        return new ArrayList<>(Arrays.asList(sum, max));
    }

    public ArrayList<Long> foodPrice() {
        long sum = 0;
        long max = 0;
        for (Integer key : foodMap.keySet()) {
            Food value = foodMap.get(key);
            sum += value.getPrice();
            max = Math.max(value.getPrice(), max);
        }
        return new ArrayList<>(Arrays.asList(sum, max));
    }

    public long sumOfAllPrice() {
        long totalSum = bottlePrice().get(0) + equPrice().get(0) + foodPrice().get(0) + money;
        if (!employees.isEmpty()) {
            for (Adventurer adventurer : employees) {
                totalSum += adventurer.sumOfAllPrice();
            }
        }
        return totalSum;
    }

    public long getMaxPrice() {
        long max = Math.max(bottlePrice().get(1), Math.max(equPrice().get(1), foodPrice().get(1)));
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

    public void sellBottle(int id) {
        String type = findInWhichBottle(id);
        Bottle bottle = bottleMap.get(id);
        switch (type) {
            case "RegularBottle":
                money += regularBottleMap.get(id).getPrice();
                Store.addBottle(regularBottleMap.get(id).getPrice(), bottle.getCapacity());
                break;
            case "ReinforcedBottle":
                money += reinforcedBottleMap.get(id).getPrice();
                Store.addBottle(reinforcedBottleMap.get(id).getPrice(), bottle.getCapacity());
                break;
            case "RecoverBottle":
                money += recoverBottleMap.get(id).getPrice();
                Store.addBottle(recoverBottleMap.get(id).getPrice(), bottle.getCapacity());
                break;
            default:
        }
    }

    public void sellEquipment(int id) {
        String type = findInWhichEquipment(id);
        Equipment equipment = equipmentMap.get(id);
        switch (type) {
            case "RegularEquipment":
                money += regularEquipmentMap.get(id).getPrice();
                Store.addEquipment(regularEquipmentMap.get(id).getPrice(), equipment.getStar());
                break;
            case "CritEquipment":
                money += critEquipmentMap.get(id).getPrice();
                Store.addEquipment(critEquipmentMap.get(id).getPrice(), equipment.getStar());
                break;
            case "EpicEquipment":
                money += epicEquipmentMap.get(id).getPrice();
                Store.addEquipment(epicEquipmentMap.get(id).getPrice(), equipment.getStar());
                break;
            default:
        }
    }

    public void sellFood(int id) {
        money += foodMap.get(id).getPrice();
        Store.addFood(foodMap.get(id).getPrice(), foodMap.get(id).getPower());
    }

    public void beAssisted(int attackPower) {
        for (Adventurer adventurer : employees) {
            if (attackPower >= this.hitPoint) {
                long assistMoney = attackPower * 10000L;
                if (adventurer.money >= assistMoney) {
                    this.money += assistMoney;
                    adventurer.money -= assistMoney;
                } else {
                    this.money += adventurer.money;
                    adventurer.money = 0;
                }
            }
        }
    }

    public long sellAll() {
        final long start = money;
        for (Map.Entry<Integer, Bottle> entry : ownBottleMap.entrySet()) {
            int id = entry.getKey();
            sellBottle(id);
            bottleMap.remove(id);
            removeBottleMap(id);
        }
        for (Equipment equipment : ownEquipment) {
            int id = equipment.getId();
            sellEquipment(id);
            equipmentMap.remove(id);
            removeEquipmentMap(id);
        }
        for (Map.Entry<Integer, Food> entry : ownFoodMap.entrySet()) {
            int id = entry.getKey();
            sellFood(id);
            foodMap.remove(id);
        }
        ownBottleMap.clear();
        ownEquipment.clear();
        ownFoodMap.clear();
        return money - start;
    }

    public void getMoreMoney(long price) {
        money += price;
    }
}
