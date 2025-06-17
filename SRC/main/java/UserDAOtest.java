package main.java;

import junit.framework.TestCase;
import org.junit.Before;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserDAOtest extends TestCase {

    private UserDAO userDAO;
    public void setUp() {
        userDAO = new UserDAO(null);
    }
    public void test(){
        User user = new User("guga", "shreki");
        userDAO.addUser(user);
        user = new User("tarash", "viri");
        userDAO.addUser(user);
        assertTrue(userDAO.checkPassword("guga", "shreki"));
        assertFalse(userDAO.checkPassword("tarash", "cxeni"));
    }
}
