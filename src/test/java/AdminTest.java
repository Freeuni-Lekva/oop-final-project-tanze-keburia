import classes.Admins;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {
    @Test
    public void testAdd() {
        Admins.addAdmin("A");
        Admins.addAdmin("AB");
        String username = "A";
        username += "B";
        assertTrue(Admins.isAdmin("AB"));
        assertFalse(Admins.isAdmin("B"));
    }
    @Test
    public void testRemove() {
        Admins.addAdmin("A");
        Admins.addAdmin("AB");
        Admins.removeAdmin("AB");
        assertFalse(Admins.isAdmin("AB"));
        assertTrue(Admins.isAdmin("A"));
    }
    @Test
    public void testGetAdmins() {
        Admins.addAdmin("A");
        Admins.addAdmin("AB");
        List<String>admins =  Admins.getAdmins();
        assertEquals(2, admins.size());
        assertTrue(admins.contains("A"));
        assertFalse(admins.contains("B"));
    }
}
