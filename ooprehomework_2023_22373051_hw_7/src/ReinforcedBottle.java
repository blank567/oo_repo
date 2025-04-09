public class ReinforcedBottle extends Bottle {
    private final long price;
    private final String type;
    private final double ratio;

    public ReinforcedBottle(int id, String name, int capacity, long price,
                            String type, double ratio) {
        super(id, name, capacity);
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
