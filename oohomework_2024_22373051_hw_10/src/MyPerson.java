import com.oocourse.spec2.main.Person;
import com.oocourse.spec2.main.Tag;

import java.util.HashMap;

public class MyPerson implements Person {
    private final int id;
    private final String name;
    private final int age;
    private final HashMap<Person, Integer> acquaintance;
    private final HashMap<Integer, Tag> tags;
    private int bestId;
    private int maxValue;
    private final HashMap<Integer, Tag> newTags;

    public MyPerson(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.acquaintance = new HashMap<>();
        this.tags = new HashMap<>();
        this.bestId = 0;
        this.maxValue = 0;
        this.newTags = new HashMap<>();
    }

    public void addToNewtags(Tag tag) {
        this.newTags.put(((MyTag) tag).getNewid(), tag);
    }

    public void delFromNewTags(Tag tag) {
        this.newTags.remove(((MyTag) tag).getNewid());
    }

    public void find(MyPerson myPerson, int value) {
        for (Tag tag : newTags.values()) {
            if (tag.hasPerson(myPerson)) {
                ((MyTag) tag).changeValue(value);
            }
        }
    }

    public int getBestId() {
        return this.bestId;
    }

    public HashMap<Person, Integer> getAcquaintance() {
        return this.acquaintance;
    }

    public void addAcquaintance(MyPerson myPerson, int value) {
        this.acquaintance.put(myPerson, value);
        if (value > maxValue) {
            maxValue = value;
            this.bestId = myPerson.getId();
        } else if (value == maxValue) {
            bestId = Math.min(bestId, myPerson.getId());
        }
    }

    public void removeAcquaintance(MyPerson myPerson) {
        for (Tag tag : this.tags.values()) {
            if (tag.hasPerson(myPerson)) {
                tag.delPerson(myPerson);
                myPerson.delFromNewTags(tag);
            }
        }
        int value = this.queryValue(myPerson);
        this.acquaintance.remove(myPerson);
        if (value == maxValue && myPerson.getId() == bestId) {
            this.bestId = 0;
            this.maxValue = 0;
            for (Person person : this.acquaintance.keySet()) {
                if (this.queryValue(person) > this.maxValue) {
                    this.maxValue = this.queryValue(person);
                    this.bestId = person.getId();
                } else if (this.queryValue(person) == this.maxValue) {
                    this.bestId = Math.min(this.bestId, person.getId());
                }
            }
        }
    }

    public void addValue(MyPerson myPerson, int value) {
        int currentValue = acquaintance.get(myPerson);
        int updatedValue = currentValue + value;
        acquaintance.put(myPerson, updatedValue);
        if (value < 0 && currentValue == maxValue) {
            this.bestId = 0;
            this.maxValue = 0;
            for (Person person : this.acquaintance.keySet()) {
                if (this.queryValue(person) > this.maxValue) {
                    this.maxValue = this.queryValue(person);
                    this.bestId = person.getId();
                } else if (this.queryValue(person) == this.maxValue) {
                    this.bestId = Math.min(this.bestId, person.getId());
                }
            }
        } else if (updatedValue > maxValue) {
            maxValue = updatedValue;
            this.bestId = myPerson.getId();
        } else if (updatedValue == maxValue) {
            bestId = Math.min(bestId, myPerson.getId());
        }
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
    public boolean containsTag(int id) {
        return tags.containsKey(id);
    }

    @Override
    public Tag getTag(int id) {
        return tags.get(id);
    }

    @Override
    public void addTag(Tag tag) {
        int id = tag.getId();
        tags.put(id, tag);
    }

    @Override
    public void delTag(int id) {
        tags.remove(id);
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
