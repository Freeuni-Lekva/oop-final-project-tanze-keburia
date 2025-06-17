import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.*;

public class UserDAOTest {
    private static Connection conn;
    private UserDAO userDao;
    private User u;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Initialize H2 in-memory database
        String url = "jdbc:mysql://localhost:3306/mysql";
        String username = "root";
        String password = "Bozartma";
        DatabaseConnector.getInstance(url, username, password);
        conn = DatabaseConnector.getConnection();
        // Create users table
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "userName VARCHAR(50) PRIMARY KEY, " +
                    "passwordHash VARCHAR(60) NOT NULL)");
        }
    }

    @Before
    public void setUp() {
        userDao = new UserDAO(conn);
    }

    @Test
    public void testAddingUser() throws NoSuchAlgorithmException {
        u = new User("mzare", "1234");
        assertFalse(userDao.userExists(u.getUserName()));
        userDao.add(u);
        assertTrue(userDao.userExists(u.getUserName()));
    }

//    @Test
//    public void testCheckPassword() throws NoSuchAlgorithmException {
//        User user = new User("testuser", "password123");
//        userDao.add(user);
//        assertTrue(userDao.checkPassword("testuser", "password123"));
//        assertFalse(userDao.checkPassword("testuser", "wrongpassword"));
//    }
}