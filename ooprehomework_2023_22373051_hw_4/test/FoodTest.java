import org.junit.Test;
import static org.junit.Assert.*;

public class FoodTest {

    @Test
    public void getPower() {
        Food food = new Food(1, "k", 2);
        assertEquals(food.getPower() ,2);
    }

    @Test
    public void getId() {
        Food food = new Food(1, "k", 2);
        assertEquals(food.getId() , 1);
    }

    @Test
    public void getName() {
        Food food = new Food(1, "k", 2);
        assertEquals(food.getName(),"k");
    }
}