package database.user;

import classes.User;
import classes.security.Hasher;
import database.database_connection.DatabaseConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class UserDAOTest {
    private Connection conn;
    private UserDAO userDAO;
    private final String TEST_USER = "testUser";
    private final String TEST_PASSWORD = "testPassword";
    private final String TEST_ADMIN = "adminUser";

    @Before
    public void setUp() throws Exception {
        conn = DatabaseConnector.getInstance().getConnection();
        userDAO = new UserDAO(conn);
        userDAO.initialize();
    }

    @After
    public void tearDown() throws Exception {
        // Clean up database after each test
        try (var stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM users");
        }
    }

    @Test
    public void testInitialize() throws SQLException {
        // Tested implicitly by setUp()
        assertTrue(true);
    }

    @Test
    public void testAddAndCheckUser() throws NoSuchAlgorithmException {
        User user = new User(TEST_USER, TEST_PASSWORD);
        userDAO.addUser(user);

        assertTrue(userDAO.userExists(TEST_USER));
        assertFalse(userDAO.userExists("nonexistentUser"));
    }

    @Test
    public void testCheckPassword() throws NoSuchAlgorithmException {
        User user = new User(TEST_USER, TEST_PASSWORD);
        userDAO.addUser(user);

        assertTrue(userDAO.checkPassword(TEST_USER, TEST_PASSWORD));
        assertFalse(userDAO.checkPassword(TEST_USER, "wrongPassword"));
        assertFalse(userDAO.checkPassword("nonexistentUser", TEST_PASSWORD));
    }

    @Test
    public void testRemoveUser() throws NoSuchAlgorithmException {
        User user = new User(TEST_USER, TEST_PASSWORD);
        userDAO.addUser(user);

        assertTrue(userDAO.removeUser(TEST_USER));
        assertFalse(userDAO.userExists(TEST_USER));
        assertFalse(userDAO.removeUser("nonexistentUser"));
    }

    @Test
    public void testGetAllUsers() throws NoSuchAlgorithmException {
        User user1 = new User("user1", "pass1");
        User user2 = new User("user2", "pass2");
        userDAO.addUser(user1);
        userDAO.addUser(user2);

        List<String> users = userDAO.getAllUsers();
        assertEquals(2, users.size());
        assertTrue(users.contains("user1"));
        assertTrue(users.contains("user2"));
    }

    @Test
    public void testGetUserCount() throws NoSuchAlgorithmException {
        assertEquals(0, userDAO.getUserCount());

        User user1 = new User("user1", "pass1");
        userDAO.addUser(user1);
        assertEquals(1, userDAO.getUserCount());

        User user2 = new User("user2", "pass2");
        userDAO.addUser(user2);
        assertEquals(2, userDAO.getUserCount());
    }

    @Test
    public void testAdminFunctions() throws SQLException, NoSuchAlgorithmException {
        User adminUser = new User(TEST_ADMIN, "adminPass");
        userDAO.addUser(adminUser);

        // Test setting admin status
        userDAO.setAdminStatus(TEST_ADMIN, true);
        assertTrue(userDAO.isAdmin(TEST_ADMIN));

        userDAO.setAdminStatus(TEST_ADMIN, false);
        assertFalse(userDAO.isAdmin(TEST_ADMIN));

        // Test non-admin user
        User regularUser = new User(TEST_USER, TEST_PASSWORD);
        userDAO.addUser(regularUser);
        assertFalse(userDAO.isAdmin(TEST_USER));
    }



    @Test
    public void testAddDuplicateUser() throws NoSuchAlgorithmException {
        User user = new User(TEST_USER, TEST_PASSWORD);
        userDAO.addUser(user);

        // Should not throw, but second add will fail silently
        userDAO.addUser(user);

        // Verify only one user exists
        assertEquals(1, userDAO.getUserCount());
    }

    @Test
    public void testPasswordHashing() throws NoSuchAlgorithmException {
        String password = "testPassword123";
        User user = new User(TEST_USER, password);
        userDAO.addUser(user);

        // Verify password is stored hashed
        try (var stmt = conn.prepareStatement("SELECT passwordHash FROM users WHERE userName = ?")) {
            stmt.setString(1, TEST_USER);
            var rs = stmt.executeQuery();
            assertTrue(rs.next());
            String storedHash = rs.getString("passwordHash");
            assertNotEquals(password, storedHash);

        } catch (SQLException e) {
            fail("Database query failed: " + e.getMessage());
        }
    }

    @Test
    public void testEmptyDatabaseOperations() {
        assertFalse(userDAO.userExists("nonexistent"));
        assertEquals(0, userDAO.getAllUsers().size());
        assertEquals(0, userDAO.getUserCount());
        assertFalse(userDAO.removeUser("nonexistent"));
    }


}