import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class categoryTest {

    @Test
    void getCategoryName() {
        category c = new category("Name 1","blue", "insert random description");
        assertEquals("Name 1", c.getCategoryName());
    }

    @Test
    void getCategoryColor() {
      category c = new category("Name 1","blue", "insert random description");
      assertEquals("blue", c.getColor());
    }

    @Test
    void getCategoryDescription() {
      category c = new category("Name 1","blue", "insert random description");
      assertEquals("insert random description", c.getDescription());
    }
}