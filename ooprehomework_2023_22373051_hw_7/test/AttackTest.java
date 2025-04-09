import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttackTest {
    Attack attack = new Attack("2000", "12", "k", "l");
    Attack attack1=new Attack("2000","12","k");

    @Test
    public void getYear() {

        assertEquals(attack.getYear(), "2000");
    }

    @Test
    public void getMonth() {
        assertEquals(attack.getMonth(), "12");
    }

    @Test
    public void getAttackName() {
        assertEquals(attack.getAttackName(), "k");
    }

    @Test
    public void getEquipmetName() {
        assertEquals(attack.getEquipmetName(), "l");
    }
}