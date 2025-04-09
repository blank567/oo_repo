import com.oocourse.spec3.exceptions.AcquaintanceNotFoundException;
import com.oocourse.spec3.exceptions.EmojiIdNotFoundException;
import com.oocourse.spec3.exceptions.EqualEmojiIdException;
import com.oocourse.spec3.exceptions.EqualMessageIdException;
import com.oocourse.spec3.exceptions.EqualPersonIdException;
import com.oocourse.spec3.exceptions.EqualRelationException;
import com.oocourse.spec3.exceptions.EqualTagIdException;
import com.oocourse.spec3.exceptions.MessageIdNotFoundException;
import com.oocourse.spec3.exceptions.PathNotFoundException;
import com.oocourse.spec3.exceptions.PersonIdNotFoundException;
import com.oocourse.spec3.exceptions.RelationNotFoundException;
import com.oocourse.spec3.exceptions.TagIdNotFoundException;
import com.oocourse.spec3.main.EmojiMessage;
import com.oocourse.spec3.main.Message;
import com.oocourse.spec3.main.Network;
import com.oocourse.spec3.main.NoticeMessage;
import com.oocourse.spec3.main.Person;
import com.oocourse.spec3.main.RedEnvelopeMessage;
import com.oocourse.spec3.main.Tag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyNetwork implements Network {
    private final HashMap<Integer, Person> id2Person = new HashMap<>();
    private final Union union = new Union();
    private int tripleSum = 0;
    private int coupleSum = 0;
    private int idcount = 0;
    private boolean ischange = false;

    private HashMap<Integer, Message> messages = new HashMap<>();
    private HashMap<Integer, Integer> emojiMessages = new HashMap<>(); //id heat

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
        myPerson1.find(myPerson2, value);
        union.addRelation(myPerson1.getId(), myPerson2.getId());
        ischange = true;
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
            myPerson1.find(myPerson2, value);
        } else {
            int value1 = myPerson1.queryValue(myPerson2);
            myPerson1.find(myPerson2, -value1);
            myPerson1.removeAcquaintance(myPerson2);
            myPerson2.removeAcquaintance(myPerson1);
            deTripleSum(myPerson1, myPerson2);
            union.removeRelation(myPerson1, myPerson2);
        }
        ischange = true;
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

    @Override
    public void addTag(int personId, Tag tag) throws
            PersonIdNotFoundException, EqualTagIdException {
        if (!containsPerson(personId)) {
            throw new MyPersonIdNotFoundException(personId);
        } else if (getPerson(personId).containsTag(tag.getId())) {
            throw new MyEqualTagIdException(tag.getId());
        }
        MyPerson myPerson = (MyPerson) getPerson(personId);
        myPerson.addTag(tag);
        ((MyTag) tag).setNewid(idcount);
        idcount++;
    }

    @Override
    public void addPersonToTag(int personId1, int personId2, int tagId)
            throws PersonIdNotFoundException, RelationNotFoundException,
            TagIdNotFoundException, EqualPersonIdException {
        if (!containsPerson(personId1)) {
            throw new MyPersonIdNotFoundException(personId1);
        } else if (!containsPerson(personId2)) {
            throw new MyPersonIdNotFoundException(personId2);
        } else if (personId1 == personId2) {
            throw new MyEqualPersonIdException(personId1);
        } else if (!getPerson(personId2).isLinked(getPerson(personId1))) {
            throw new MyRelationNotFoundException(personId1, personId2);
        } else if (!getPerson(personId2).containsTag(tagId)) {
            throw new MyTagIdNotFoundException(tagId);
        } else if (getPerson(personId2).getTag(tagId).hasPerson(getPerson(personId1))) {
            throw new MyEqualPersonIdException(personId1);
        }
        MyTag mytag = (MyTag) getPerson(personId2).getTag(tagId);
        if (mytag.getSize() <= 1111) {
            mytag.addPerson(getPerson(personId1));
            ((MyPerson) getPerson(personId1)).addToNewtags(mytag);
        }
    }

    @Override
    public int queryTagValueSum(int personId, int tagId)
            throws PersonIdNotFoundException, TagIdNotFoundException {
        if (!containsPerson(personId)) {
            throw new MyPersonIdNotFoundException(personId);
        } else if (!getPerson(personId).containsTag(tagId)) {
            throw new MyTagIdNotFoundException(tagId);
        }
        return getPerson(personId).getTag(tagId).getValueSum();
    }

    @Override
    public int queryTagAgeVar(int personId, int tagId)
            throws PersonIdNotFoundException, TagIdNotFoundException {
        if (!containsPerson(personId)) {
            throw new MyPersonIdNotFoundException(personId);
        } else if (!getPerson(personId).containsTag(tagId)) {
            throw new MyTagIdNotFoundException(tagId);
        }
        return getPerson(personId).getTag(tagId).getAgeVar();
    }

    @Override
    public void delPersonFromTag(int personId1, int personId2, int tagId)
            throws PersonIdNotFoundException, TagIdNotFoundException {
        if (!containsPerson(personId1)) {
            throw new MyPersonIdNotFoundException(personId1);
        } else if (!containsPerson(personId2)) {
            throw new MyPersonIdNotFoundException(personId2);
        } else if (!getPerson(personId2).containsTag(tagId)) {
            throw new MyTagIdNotFoundException(tagId);
        } else if (!getPerson(personId2).getTag(tagId).hasPerson(getPerson(personId1))) {
            throw new MyPersonIdNotFoundException(personId1);
        }
        MyTag mytag = (MyTag) getPerson(personId2).getTag(tagId);
        mytag.delPerson(getPerson(personId1));
        ((MyPerson) getPerson(personId1)).delFromNewTags(mytag);
    }

    @Override
    public void delTag(int personId, int tagId)
            throws PersonIdNotFoundException, TagIdNotFoundException {
        if (!containsPerson(personId)) {
            throw new MyPersonIdNotFoundException(personId);
        } else if (!getPerson(personId).containsTag(tagId)) {
            throw new MyTagIdNotFoundException(tagId);
        }
        getPerson(personId).delTag(tagId);
    }

    @Override
    public boolean containsMessage(int id) {
        return messages.containsKey(id);
    }

    @Override
    public void addMessage(Message message)
            throws EqualMessageIdException, EmojiIdNotFoundException, EqualPersonIdException {
        if (containsMessage(message.getId())) {
            throw new MyEqualMessageIdException(message.getId());
        } else if (message instanceof EmojiMessage) {
            if (!containsEmojiId(((EmojiMessage) message).getEmojiId())) {
                throw new MyEmojiIdNotFoundException(((EmojiMessage) message).getEmojiId());
            }
        }
        if (message.getType() == 0 && message.getPerson1().equals(message.getPerson2())) {
            throw new MyEqualPersonIdException(message.getPerson1().getId());///////////
        }
        messages.put(message.getId(), message);
    }

    @Override
    public Message getMessage(int id) {
        return messages.get(id);
    }

    @Override
    public void sendMessage(int id)
            throws RelationNotFoundException, MessageIdNotFoundException, TagIdNotFoundException {
        if (!containsMessage(id)) {
            throw new MyMessageIdNotFoundException(id);
        } else if (getMessage(id).getType() == 0 &&
                !(getMessage(id).getPerson1().isLinked(getMessage(id).getPerson2()))) {
            throw new MyRelationNotFoundException(getMessage(id).getPerson1().getId(),
                    getMessage(id).getPerson2().getId());
        } else if (getMessage(id).getType() == 1 &&
                !getMessage(id).getPerson1().containsTag(getMessage(id).getTag().getId())) {
            throw new MyTagIdNotFoundException(getMessage(id).getTag().getId());
        }
        Message message = getMessage(id);
        messages.remove(id);
        MyPerson person1 = (MyPerson) message.getPerson1();
        MyPerson person2 = (MyPerson) message.getPerson2();
        int socialValue = message.getSocialValue();
        if (message.getType() == 0) {
            person1.addSocialValue(socialValue);
            person2.addSocialValue(socialValue);
            if (message instanceof RedEnvelopeMessage) {
                person1.addMoney(-((RedEnvelopeMessage) message).getMoney());
                person2.addMoney(((RedEnvelopeMessage) message).getMoney());
            } else if (message instanceof EmojiMessage) {
                emojiMessages.merge(((EmojiMessage) message).getEmojiId(), 1, Integer::sum);
            }
            person2.addMessageAtFirst(message);
        } else if (message.getType() == 1) {
            person1.addSocialValue(socialValue);
            for (Person person : ((MyTag) message.getTag()).getPersonsInTag()) {
                person.addSocialValue(socialValue);
            }
            if (message instanceof RedEnvelopeMessage) {
                int money = (message.getTag().getSize() == 0) ? 0 :
                        ((RedEnvelopeMessage) message).getMoney() / message.getTag().getSize();
                person1.addMoney(-money * message.getTag().getSize());
                for (Person person : ((MyTag) message.getTag()).getPersonsInTag()) {
                    person.addMoney(money);
                }
            } else if (message instanceof EmojiMessage) {
                emojiMessages.merge(((EmojiMessage) message).getEmojiId(), 1, Integer::sum);
            }
        }
    }

    @Override
    public int querySocialValue(int id) throws PersonIdNotFoundException {
        if (!containsPerson(id)) {
            throw new MyPersonIdNotFoundException(id);
        }
        return getPerson(id).getSocialValue();
    }

    @Override
    public List<Message> queryReceivedMessages(int id) throws PersonIdNotFoundException {
        if (!containsPerson(id)) {
            throw new MyPersonIdNotFoundException(id);
        }
        return getPerson(id).getReceivedMessages();
    }

    @Override
    public boolean containsEmojiId(int id) {
        return emojiMessages.containsKey(id);
    }

    @Override
    public void storeEmojiId(int id) throws EqualEmojiIdException {
        if (containsEmojiId(id)) {
            throw new MyEqualEmojiIdException(id);
        }
        emojiMessages.put(id, 0);
    }

    @Override
    public int queryMoney(int id) throws PersonIdNotFoundException {
        if (!containsPerson(id)) {
            throw new MyPersonIdNotFoundException(id);
        }
        return getPerson(id).getMoney();
    }

    @Override
    public int queryPopularity(int id) throws EmojiIdNotFoundException {
        if (!containsEmojiId(id)) {
            throw new MyEmojiIdNotFoundException(id);
        }
        return emojiMessages.get(id);
    }

    @Override
    public int deleteColdEmoji(int limit) {
        emojiMessages.entrySet().removeIf(entry -> entry.getValue() < limit);
        messages.entrySet().removeIf(entry -> entry.getValue() instanceof EmojiMessage
                && !containsEmojiId(((EmojiMessage) entry.getValue()).getEmojiId()));
        return emojiMessages.size();
    }

    @Override
    public void clearNotices(int personId) throws PersonIdNotFoundException {
        if (!containsPerson(personId)) {
            throw new MyPersonIdNotFoundException(personId);
        }
        List<Message> messages = id2Person.get(personId).getMessages();
        //        for(Iterator<Message> iterator = messages.iterator(); iterator.hasNext();){
        //            Message message = iterator.next();
        //            if (message instanceof NoticeMessage) {
        //                iterator.remove();
        //            }
        //        }
        messages.removeIf(message -> message instanceof NoticeMessage);
    }

    @Override
    public int queryBestAcquaintance(int id)
            throws PersonIdNotFoundException, AcquaintanceNotFoundException {
        if (!containsPerson(id)) {
            throw new MyPersonIdNotFoundException(id);
        } else if (((MyPerson) getPerson(id)).getAcquaintance().size() == 0) {
            throw new MyAcquaintanceNotFoundException(id);
        }
        MyPerson myPerson = (MyPerson) getPerson(id);
        return myPerson.getBestId();
    }

    @Override
    public int queryCoupleSum() {
        if (!ischange) {
            return coupleSum;
        }
        coupleSum = 0;
        HashSet<MyPerson> visitors = new HashSet<>();
        for (Person person : id2Person.values()) {
            if (!visitors.contains(person) && ((MyPerson) person).getAcquaintance().size() != 0) {
                int id = ((MyPerson) person).getBestId();
                MyPerson person1 = (MyPerson) getPerson(id);
                if (person1.getBestId() == person.getId()) {
                    coupleSum++;
                    visitors.add(person1);
                }
            }
        }
        ischange = false;
        return coupleSum;
    }

    @Override
    public int queryShortestPath(int id1, int id2)
            throws PersonIdNotFoundException, PathNotFoundException {
        if (!containsPerson(id1)) {
            throw new MyPersonIdNotFoundException(id1);
        } else if (!containsPerson(id2)) {
            throw new MyPersonIdNotFoundException(id2);
        } else if (!union.isConnected(id1, id2)) {
            throw new MyPathNotFoundException(id1, id2);
        }
        if (id1 == id2) {
            return 0;
        }
        Person person1 = getPerson(id1);
        Person person2 = getPerson(id2);
        HashMap<Person, Person> parentMap = new HashMap<>();
        Queue<Person> queue = new LinkedList<>();
        HashSet<Person> visited = new HashSet<>();
        queue.add(person1);
        visited.add(person1);
        while (!queue.isEmpty()) {
            Person current = queue.poll();
            if (current.getId() == id2) {
                return getPathLength(parentMap, person1, person2);
            }
            for (Person neighbor : ((MyPerson) current).getAcquaintance().keySet()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    parentMap.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }
        return 0;
    }

    public int getPathLength(HashMap<Person, Person> parentMap, Person start, Person target) {
        int length = 0;
        Person current = target;
        while (!current.equals(start)) {
            current = parentMap.get(current);
            length++;
        }
        return length - 1;
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

    public Message[] getMessages() {
        return null;
    }

    public int[] getEmojiIdList() {
        return null;
    }

    public int[] getEmojiHeatList() {
        return null;
    }
}
