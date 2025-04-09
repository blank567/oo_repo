public class Store {
    private static long bottlePrice;
    private static int bottleCapacity;
    private static int bottleCount;
    private static long equipmentPrice;
    private static int equipmentStar;
    private static int equipmentCount;
    private static long foodPrice;
    private static int foodPower;
    private static int foodCount;

    public static void addBottle(long price, int capacity) {
        bottlePrice += price;
        bottleCapacity += capacity;
        bottleCount += 1;
    }

    public static void addEquipment(long price, int star) {
        equipmentCount += 1;
        equipmentStar += star;
        equipmentPrice += price;
    }

    public static void addFood(long price, int power) {
        foodCount += 1;
        foodPower += power;
        foodPrice += price;
    }

    public long getBottlePrice() {
        return bottlePrice / bottleCount;
    }

    public int getBottleCapacity() {
        return bottleCapacity / bottleCount;
    }

    public long getEquipmentPrice() {
        return equipmentPrice / equipmentCount;
    }

    public int getEquipmentStar() {
        return equipmentStar / equipmentCount;
    }

    public long getFoodPrice() {
        return foodPrice / foodCount;
    }

    public int getFoodPower() {
        return foodPower / foodCount;
    }
}
