import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DairyTest {
    private Dairy dairy;
    private Dairy dairy1;

    @Before
    public void setUp() {
        dairy = new Dairy("2000", "10", 1, "Adv1", "Event1");
        dairy1 = new Dairy("2000", "10", 2, "Adv1", "Event1");
    }

    @Test
    public void testReadDairyMatch() {
        boolean foundMatch = dairy.readDairy("2000", "10");
        assertTrue(foundMatch);
        foundMatch = dairy1.readDairy("2000", "10");
        assertTrue(foundMatch);
    }

    @Test
    public void testReadDairyNoMatch() {
        boolean foundMatch = dairy.readDairy("2001", "10");
        assertFalse(foundMatch);
        foundMatch = dairy1.readDairy("2001", "10");
        assertFalse(foundMatch);
    }

    @Test
    public void testReadDairyWithAdvName2() {

        Dairy dairy = new Dairy("2023", "10", "Adv1", "Adv2", "Event2");
        boolean foundMatch = dairy.readDairy("2023", "10");
        assertTrue(foundMatch);
        foundMatch = dairy.readDairy("2023", "11");
        assertFalse(foundMatch);
        //String expectedOutput = "2023/10 Adv1 attacked Adv2 with Event2";
    }
}