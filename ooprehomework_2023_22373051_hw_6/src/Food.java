/**
 * @noinspection checkstyle:EmptyLineSeparator
 */
public class Food {
    private final int id;
    private final String name;
    private final int power;
    private final long price;

    public Food(int id, String name, int power, long price) {
        this.power = power;
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getPower() {
        return power;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }
}
