import com.oocourse.spec3.main.Person;
import com.oocourse.spec3.main.Tag;

import java.util.HashSet;

public class MyTag implements Tag {
    private final int id;
    private final HashSet<Person> personsInTag;
    private int valueSum;
    private int ageSum;
    private int newid;

    public MyTag(int id) {
        this.id = id;
        this.personsInTag = new HashSet<>();
        this.valueSum = 0;
        this.ageSum = 0;
        this.newid = -1;
    }

    public HashSet<Person> getPersonsInTag() {
        return personsInTag;
    }

    public void changeValue(int value) {
        this.valueSum += 2 * value;
    }

    public void setNewid(int newid) {
        this.newid = newid;
    }

    public int getNewid() {
        return newid;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tag)) {
            return false;
        }
        return ((Tag) obj).getId() == id;
    }

    @Override
    public void addPerson(Person person) {
        for (Person person1 : personsInTag) {
            if (person.isLinked(person1)) {
                valueSum += 2 * person.queryValue(person1);
            }
        }
        personsInTag.add(person);
        this.ageSum += person.getAge();
    }

    @Override
    public boolean hasPerson(Person person) {
        return personsInTag.contains(person);
    }

    @Override
    public int getValueSum() {
        return valueSum;
    }

    @Override
    public int getAgeMean() {
        return personsInTag.size() == 0 ? 0 : this.ageSum / personsInTag.size();
    }

    @Override
    public int getAgeVar() {
        int ageVar = 0;
        int ageMean = this.getAgeMean();
        for (Person person : personsInTag) {
            ageVar += ((person.getAge() - ageMean) * (person.getAge() - ageMean));
        }
        return personsInTag.size() == 0 ? 0 : ageVar / personsInTag.size();
    }

    @Override
    public void delPerson(Person person) {
        personsInTag.remove(person);
        for (Person person1 : personsInTag) {
            if (person1.isLinked(person)) {
                valueSum -= 2 * person1.queryValue(person);
            }
        }
        this.ageSum -= person.getAge();
    }

    @Override
    public int getSize() {
        return personsInTag.size();
    }
}
