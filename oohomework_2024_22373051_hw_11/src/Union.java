import com.oocourse.spec3.main.Person;

import java.util.HashMap;
import java.util.HashSet;

public class Union {
    private HashMap<Integer, Integer> parent;

    public Union() {
        parent = new HashMap<>();
    }

    public void addUnion(int id1, int id2) {
        if (!parent.containsKey(id1)) {
            parent.put(id1, id1);
        }
        if (!parent.containsKey(id2)) {
            parent.put(id2, id2);
        }
    }

    private int find(int x) {
        if (parent.get(x) != x) {
            parent.put(x, find(parent.get(x))); // 路径压缩
        }
        return parent.get(x);
    }

    public void addRelation(int id1, int id2) {
        int rootX = find(id1);
        int rootY = find(id2);
        if (rootX == rootY) {
            return;
        }

        parent.put(rootY, rootX);
        //        if (rank.get(rootX) < rank.get(rootY)) {
        //            parent.put(rootX, rootY);
        //        } else if (rank.get(rootX) > rank.get(rootY)) {
        //            parent.put(rootY, rootX);
        //        } else {
        //            parent.put(rootY, rootX);
        //            rank.put(rootX, rank.get(rootX) + 1); // 更新树的深度
        //        }
    }

    public void removeRelation(MyPerson myPerson1, MyPerson myPerson2) {
        HashSet<MyPerson> visited = new HashSet<>();
        dfs(myPerson1, visited);
        for (MyPerson myPerson : visited) {
            parent.put(myPerson.getId(), myPerson1.getId());
            //            if (myPerson.getId() == myPerson1.getId()) {
            //                rank.put(myPerson.getId(), 1);
            //            } else {
            //                rank.put(myPerson.getId(), 0);
            //            }
        }

        if (!visited.contains(myPerson2)) {
            visited.clear();
            dfs(myPerson2, visited);
            for (MyPerson myPerson : visited) {
                parent.put(myPerson.getId(), myPerson2.getId());
                //                if (myPerson.getId() == myPerson1.getId()) {
                //                    rank.put(myPerson.getId(), 1);
                //                } else {
                //                    rank.put(myPerson.getId(), 0);
                //                }
            }
        }
    }

    public void dfs(MyPerson person, HashSet<MyPerson> visited) {
        visited.add(person);

        for (Person friend : person.getAcquaintance().keySet()) {
            if (!visited.contains((MyPerson) friend)) {
                dfs((MyPerson) friend, visited);
            }
        }
    }

    public boolean isConnected(int id1, int id2) {
        return find(id1) == find(id2);
    }

    public int countRoots() {
        HashSet<Integer> roots = new HashSet<>();
        for (int id : parent.keySet()) {
            roots.add(find(id));
        }
        return roots.size();
    }
}
