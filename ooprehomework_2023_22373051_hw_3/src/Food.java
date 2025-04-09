/** @noinspection checkstyle:EmptyLineSeparator*/
public class Food {
    private int id;
    private String name;
    private int power;

    public Food(int id, String name, int power) {
        this.power = power;
        this.id = id;
        this.name = name;
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

    public void trash() {

    }
}
