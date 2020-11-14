import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class memberTest {

    @Test
    void authenticate() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        assertEquals(true, m.authenticate("2"));
    }

    @Test
    void changePassword() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        assertEquals(true, m.changePassword("2", "3"));
    }

    @Test
    void isAdmin() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        assertEquals(true, m.isAdmin());
    }

    @Test
    void getName() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        assertEquals("Ezekiel Test", m.getName());
    }

    @Test
    void getId() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        assertEquals(2, m.getId());
    }

    @Test
    void getColor() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        assertEquals("Breen", m.getColor());
    }

    @Test
    void getAdditional() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        assertEquals("Really good at Gambling", m.getAdditional());
    }

    @Test
    void isDeleted() {
        member m = new member("Ezekiel Test", "2", "Breen", "Really good at Gambling", true);
        m.setDeleted();
        assertEquals(true, m.isDeleted());
    }
}