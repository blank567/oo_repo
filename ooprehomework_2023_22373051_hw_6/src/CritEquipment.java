public class CritEquipment extends Equipment {
    private final long price;
    private final String type;
    private final int critical;

    public CritEquipment(int id, String name, int star, long price, String type, int critical) {
        super(id, name, star);
        this.price = price;
        this.type = type;
        this.critical = critical;
    }

    public long getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public int getCritical() {
        return critical;
    }
}
