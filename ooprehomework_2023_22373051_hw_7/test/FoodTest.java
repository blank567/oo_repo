import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FoodTest {

    Food food = new Food(1, "k", 2, 3);

    @Test
    public void getPower() {
        assertEquals(food.getPower(), 2);
    }

    @Test
    public void getId() {
        assertEquals(food.getId(), 1);
    }

    @Test
    public void getName() {
        assertEquals(food.getName(), "k");
    }

    @Test
    public void getPrice() {
        assertEquals(food.getPrice(), 3);
    }
}