import com.oocourse.spec1.exceptions.EqualPersonIdException;
import com.oocourse.spec1.exceptions.EqualRelationException;
import com.oocourse.spec1.exceptions.PersonIdNotFoundException;
import com.oocourse.spec1.exceptions.RelationNotFoundException;
import com.oocourse.spec1.main.Network;
import com.oocourse.spec1.main.Person;

import java.util.HashMap;

public class MyNetwork implements Network {
    private final HashMap<Integer, Person> id2Person = new HashMap<>();
    private Union union = new Union();
    private int tripleSum = 0;

    @Override
    public boolean containsPerson(int id) {
        return id2Person.containsKey(id);
    }

    @Override
    public Person getPerson(int id) {
        return id2Person.get(id);
    }

    @Override
    public void addPerson(Person person) throws EqualPersonIdException {
        if (containsPerson(person.getId())) {
            throw new MyEqualPersonIdException(person.getId());
        }
        id2Person.put(person.getId(), person);
        union.addUnion(person.getId(), person.getId());
    }

    @Override
    public void addRelation(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualRelationException {
        if (!containsPerson(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!containsPerson(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (getPerson(id1).isLinked(getPerson(id2))) {
            throw new MyEqualRelationException(id1, id2);
        }
        MyPerson myPerson1 = (MyPerson) getPerson(id1);
        MyPerson myPerson2 = (MyPerson) getPerson(id2);
        addTripleSum(myPerson1, myPerson2);
        myPerson1.addAcquaintance(myPerson2, value);
        myPerson2.addAcquaintance(myPerson1, value);
        union.addRelation(myPerson1.getId(), myPerson2.getId());
    }

    @Override
    public void modifyRelation(int id1, int id2, int value)
            throws PersonIdNotFoundException, EqualPersonIdException, RelationNotFoundException {
        if (!containsPerson(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!containsPerson(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (id1 == id2) {
            throw new MyEqualPersonIdException(id1);
        } else if (!getPerson(id1).isLinked(getPerson(id2))) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        MyPerson myPerson1 = (MyPerson) getPerson(id1);
        MyPerson myPerson2 = (MyPerson) getPerson(id2);
        if (queryValue(id1, id2) + value > 0) {
            myPerson1.addValue(myPerson2, value);
            myPerson2.addValue(myPerson1, value);
        } else {
            myPerson1.removeAcquaintance(myPerson2);
            myPerson2.removeAcquaintance(myPerson1);
            deTripleSum(myPerson1, myPerson2);
            union.removeRelation(myPerson1, myPerson2);
        }
    }

    @Override
    public int queryValue(int id1, int id2)
            throws PersonIdNotFoundException, RelationNotFoundException {
        if (!containsPerson(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!containsPerson(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (!getPerson(id1).isLinked(getPerson(id2))) {
            throw new MyRelationNotFoundException(id1, id2);
        }
        return getPerson(id1).queryValue(getPerson(id2));
    }

    @Override
    public boolean isCircle(int id1, int id2) throws PersonIdNotFoundException {
        if (!containsPerson(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!containsPerson(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        }

        return union.isConnected(id1, id2);
    }

    @Override
    public int queryBlockSum() {
        return union.countRoots();
    }

    @Override
    public int queryTripleSum() {
        return tripleSum;
    }

    public void addTripleSum(MyPerson myPerson1, MyPerson myPerson2) {
        for (Person person : myPerson1.getAcquaintance().keySet()) {
            if (person.isLinked(myPerson2)) {
                tripleSum++;
            }
        }
    }

    public void deTripleSum(MyPerson myPerson1, MyPerson myPerson2) {
        for (Person person : myPerson1.getAcquaintance().keySet()) {
            if (person.isLinked(myPerson2)) {
                tripleSum--;
            }
        }
    }

    public Person[] getPersons() {
        return null;
    }
}
