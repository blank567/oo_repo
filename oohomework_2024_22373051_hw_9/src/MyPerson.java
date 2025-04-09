import com.oocourse.spec1.main.Person;

import java.util.HashMap;

public class MyPerson implements Person {
    private int id;
    private String name;
    private int age;
    private HashMap<Person, Integer> acquaintance = new HashMap<>();

    public MyPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public HashMap<Person, Integer> getAcquaintance() {
        return this.acquaintance;
    }

    public void addAcquaintance(MyPerson myPerson, int value) {
        this.acquaintance.put(myPerson, value);
    }

    public void removeAcquaintance(MyPerson myPerson) {
        this.acquaintance.remove(myPerson);
    }

    public void addValue(MyPerson myPerson, int value) {
        int currentValue = acquaintance.get(myPerson);
        int updatedValue = currentValue + value;
        acquaintance.put(myPerson, updatedValue);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getAge() {
        return this.age;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) {
            return false;
        } else {
            return ((Person) obj).getId() == this.id;
        }
    }

    @Override
    public boolean isLinked(Person person) {
        return person.getId() == id || acquaintance.containsKey(person);
    }

    @Override
    public int queryValue(Person person) {
        return acquaintance.getOrDefault(person, 0);
    }

    public boolean strictEquals(Person person) {
        return true;
    }
}
