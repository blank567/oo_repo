public class RegularBottle extends Bottle {
    private final long price;
    private final String type;

    public long getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public RegularBottle(int id, String name, int capacity, long price, String type) {
        super(id, name, capacity);
        this.price = price;
        this.type = type;
    }
}
