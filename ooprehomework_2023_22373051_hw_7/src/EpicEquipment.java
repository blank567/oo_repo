public class EpicEquipment extends Equipment {
    private final long price;
    private final String type;
    private final double ratio;

    public EpicEquipment(int id, String name, int star, long price, String type, double ratio) {
        super(id, name, star);
        this.price = price;
        this.type = type;
        this.ratio = ratio;
    }

    public long getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public double getRatio() {
        return ratio;
    }
}
