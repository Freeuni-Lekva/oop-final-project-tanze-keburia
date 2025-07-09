package database;

import classes.User;
import database.database_connection.DatabaseConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {
    private static Connection conn;
    private UserDAO userDao;
    private User testUser1;
    private User testUser2;

    @BeforeClass
    public static void setUpClass() throws Exception {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        conn = dbc.getConnection();
        assertNotNull(conn);

        // Set autocommit to false for transaction control
        conn.setAutoCommit(false);

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("CREATE TABLE users (" +
                    "userName VARCHAR(50) PRIMARY KEY, " +
                    "passwordHash VARCHAR(60) NOT NULL)");
            conn.commit();
        }
    }

    @Before
    public void setUp() throws SQLException, NoSuchAlgorithmException {
        // Start with a clean slate for each test
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
            conn.commit();
        }

        userDao = new UserDAO(conn);
        testUser1 = new User("testuser1", "password123");
        testUser2 = new User("testuser2", "securePass456");
    }

    @After
    public void tearDown() throws SQLException {
        // Rollback any uncommitted changes after each test
        conn.rollback();
    }

    @Test
    public void testAddUser() throws SQLException {
        assertFalse(userDao.userExists(testUser1.getUserName()));
        userDao.addUser(testUser1);
        assertTrue(userDao.userExists(testUser1.getUserName()));
        conn.commit(); // Explicitly commit for this test
    }

    @Test
    public void testCheckPassword() throws NoSuchAlgorithmException, SQLException {
        userDao.addUser(testUser1);
        conn.commit();

        assertTrue(userDao.checkPassword(testUser1.getUserName(), "password123"));
        assertFalse(userDao.checkPassword(testUser1.getUserName(), "wrongpassword"));
    }

    @Test
    public void testUserExists() throws SQLException {
        assertFalse(userDao.userExists(testUser1.getUserName()));
        userDao.addUser(testUser1);
        conn.commit();

        assertTrue(userDao.userExists(testUser1.getUserName()));
        assertFalse(userDao.userExists("nonexistent"));
    }

    @Test
    public void testRemoveUser() throws SQLException {
        userDao.addUser(testUser1);
        conn.commit();

        assertTrue(userDao.userExists(testUser1.getUserName()));
        assertTrue(userDao.removeUser(testUser1.getUserName()));
        assertFalse(userDao.userExists(testUser1.getUserName()));
        conn.commit();
    }

    @Test
    public void testGetAllUsers() throws SQLException {
        assertEquals(0, userDao.getAllUsers().size());

        userDao.addUser(testUser1);
        userDao.addUser(testUser2);
        conn.commit();

        List<String> users = userDao.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains(testUser1.getUserName()));
        assertTrue(users.contains(testUser2.getUserName()));
    }

    @Test
    public void testGetUserCount() throws SQLException {
        assertEquals(0, userDao.getUserCount());

        userDao.addUser(testUser1);
        conn.commit();
        assertEquals(1, userDao.getUserCount());

        userDao.addUser(testUser2);
        conn.commit();
        assertEquals(2, userDao.getUserCount());
    }

    @Test
    public void testInitializeCanBeCalledMultipleTimes() throws SQLException {
        userDao.initialize();
        conn.commit();

        userDao.addUser(testUser1);
        conn.commit();
        assertEquals(1, userDao.getUserCount());

        userDao.initialize();
        conn.commit();
        assertEquals(0, userDao.getUserCount());
    }
}