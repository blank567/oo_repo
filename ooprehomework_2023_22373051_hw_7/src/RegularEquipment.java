public class RegularEquipment extends Equipment {
    private final long price;
    private final String type;

    public RegularEquipment(int id, String name, int star, long price, String type) {
        super(id, name, star);
        this.price = price;
        this.type = type;
    }

    public long getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
