package test;

import classes.Hasher;
import classes.User;
import junit.framework.TestCase;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;



public class UserTest extends TestCase {
    @Test
    public void test1() throws NoSuchAlgorithmException {
        User u = new User("Giorgi", "1234");
        assertEquals("Giorgi", u.getUserName());
        String hashedPassword = Hasher.hashPassword("1234");
        assertEquals(hashedPassword, u.getPassword());
    }
}
