public class Bottle {
    private final int id;
    private final String name;
    private final int capacity;
    private boolean isEmpty;

    public Bottle(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.isEmpty = false;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
