import org.junit.Test;
import static org.junit.Assert.*;

public class AttackTest {

    @Test
    public void getYear() {
        Attack attack = new Attack("2000", "12", "k", "l");
        assertEquals(attack.getYear(), "2000");
    }

    @Test
    public void getMonth() {
        Attack attack = new Attack("2000", "12", "k", "l");
        assertEquals(attack.getMonth(), "12");
    }

    @Test
    public void getAttackName() {
        Attack attack = new Attack("2000", "12", "k", "l");
        assertEquals(attack.getAttackName(), "k");
    }

    @Test
    public void getEquipmetName() {
        Attack attack = new Attack("2000", "12", "k", "l");
        assertEquals(attack.getEquipmetName(), "l");
    }
}