public class Equipment {
    private int id;
    private String name;
    private int star;

    public Equipment(int id, String name, int star) {
        this.id = id;
        this.name = name;
        this.star = star;
    }

    public String getName() {
        return name;
    }

    public int getStar() {
        return star;
    }

    public void getMoreStar() {
        star++;
    }

    public void trash() {

    }
}
