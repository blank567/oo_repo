import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class MethodTest {
    Method method = new Method();

    @Test
    public void process() {
        ArrayList<String> res = new ArrayList<>();
        String string1 = "2023/10-Name-Description";
        String string2 = "2023/11-Name@OtherName-Description";
        String string3 = "2023/12-Name@#-Description";
        String string4 = "2023/01";
        res.add("1");
        res.add("2023");
        res.add("10");
        res.add("Name");
        res.add("Description");
        assertEquals(res, method.process(string1));
        res.clear();
        res.add("2");
        res.add("2023");
        res.add("11");
        res.add("Name");
        res.add("OtherName");
        res.add("Description");
        assertEquals(res, method.process(string2));
        res.clear();
        res.add("3");
        res.add("2023");
        res.add("12");
        res.add("Name");
        res.add("Description");
        assertEquals(res, method.process(string3));
        method.process(string4);
        res.clear();
        res.add("2023");
        res.add("01");
        assertEquals(res, method.process(string4));
    }

    @Test
    public void judge() {
        ArrayList<String> names = new ArrayList<>();
        String name1 = "l";
        String name2 = "p";
        names.add(name1);
        assertEquals(method.judge(name1, names), true);
        assertEquals(method.judge(name2, names), false);
    }

    @Test
    public void judgeBot() {
    }

    @Test
    public void judgeEqu1() {
    }

    @Test
    public void judgeEqu2() {
    }
}