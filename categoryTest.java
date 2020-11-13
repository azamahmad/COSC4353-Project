import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class categoryTest {

    @Test
    void getCategoryName() {
        category c = new category("Name 1","blue", "insert random description", 3);
        assertEquals("Name 1", c.getCategoryName());
    }

    @Test
    void getCategoryColor() {
      category c = new category("Name 1","blue", "insert random description", 3);
      assertEquals("blue", c.getColor());
    }

    @Test
    void getCategoryDescription() {
        category c = new category("Name 1","blue", "insert random description", 3);
        assertEquals("insert random description", c.getDescription());
    }
    @Test
    void getCategoryId(){
        category c = new category("Name 1","blue", "insert random description", 3);
        assertEquals(3, c.getId());
    }
}