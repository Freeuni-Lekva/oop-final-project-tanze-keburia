import database.database_connection.DatabaseConnector;
import database.FriendsDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class FriendsDAOTest {
    private static Connection conn;
    private FriendsDAO friendsDAO;

    @BeforeClass
    public static void setUpClass() throws Exception {
        conn = DatabaseConnector.getInstance().getConnection();
        assertNotNull(conn);
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS friends");
            stmt.execute("CREATE TABLE friends (" +
                    "user_a VARCHAR(255), " +
                    "user_b VARCHAR(255), " +
                    "PRIMARY KEY (user_a, user_b), " +
                    "CHECK (user_a < user_b))");
        }
    }

    @Before
    public void setUp() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM friends");
        }
        friendsDAO = new FriendsDAO(conn);
    }

    @After
    public void tearDown() throws SQLException {

        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM friends");
        }
    }

    @Test
    public void testAddFriends() {
        friendsDAO.addFriends("Alice", "Bob");

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        List<String> bobFriends = friendsDAO.getFriends("Bob");

        assertTrue(aliceFriends.contains("Bob"));
        assertTrue(bobFriends.contains("Alice"));
        assertEquals(1, aliceFriends.size());
        assertEquals(1, bobFriends.size());
    }

    @Test
    public void testAddFriendsReverseOrder() {
        friendsDAO.addFriends("Bob", "Alice");

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        List<String> bobFriends = friendsDAO.getFriends("Bob");

        assertTrue(aliceFriends.contains("Bob"));
        assertTrue(bobFriends.contains("Alice"));
        assertEquals(1, aliceFriends.size());
        assertEquals(1, bobFriends.size());
    }

    @Test
    public void testRemoveFriends() {
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.removeFriends("Alice", "Bob");

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        List<String> bobFriends = friendsDAO.getFriends("Bob");

        assertFalse(aliceFriends.contains("Bob"));
        assertFalse(bobFriends.contains("Alice"));
        assertEquals(0, aliceFriends.size());
        assertEquals(0, bobFriends.size());
    }

    @Test
    public void testRemoveFriendsReverseOrder() {
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.removeFriends("Bob", "Alice");

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        List<String> bobFriends = friendsDAO.getFriends("Bob");

        assertFalse(aliceFriends.contains("Bob"));
        assertFalse(bobFriends.contains("Alice"));
        assertEquals(0, aliceFriends.size());
        assertEquals(0, bobFriends.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFriendsWithNullFirstUser() {
        friendsDAO.addFriends(null, "Bob");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFriendsWithNullSecondUser() {
        friendsDAO.addFriends("Alice", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddFriendsWithSameUser() {
        friendsDAO.addFriends("Alice", "Alice");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFriendsWithNullFirstUser() {
        friendsDAO.removeFriends(null, "Bob");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFriendsWithNullSecondUser() {
        friendsDAO.removeFriends("Alice", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveFriendsWithSameUser() {
        friendsDAO.removeFriends("Alice", "Alice");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFriendsWithNullUsername() {
        friendsDAO.getFriends(null);
    }

    @Test
    public void testGetFriendsWhenNoFriends() {
        List<String> friends = friendsDAO.getFriends("Alice");
        assertTrue(friends.isEmpty());
    }

    @Test
    public void testMultipleFriends() {
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.addFriends("Alice", "Charlie");
        friendsDAO.addFriends("Alice", "Dana");

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        assertEquals(3, aliceFriends.size());
        assertTrue(aliceFriends.contains("Bob"));
        assertTrue(aliceFriends.contains("Charlie"));
        assertTrue(aliceFriends.contains("Dana"));

        List<String> bobFriends = friendsDAO.getFriends("Bob");
        assertEquals(1, bobFriends.size());
        assertTrue(bobFriends.contains("Alice"));
    }

    @Test
    public void testInitialize() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS friends");
        }

        try (Statement stmt = conn.createStatement()) {
            stmt.executeQuery("SELECT * FROM friends"); // Should throw exception
            fail("Expected SQLException");
        } catch (SQLException e) {

        }
        friendsDAO.initialize();
        try (Statement stmt = conn.createStatement()) {
            stmt.executeQuery("SELECT * FROM friends"); // Should not throw exception
        }
    }

    @Test(expected = RuntimeException.class)
    public void testAddDuplicateFriends() {
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.addFriends("Alice", "Bob"); // Should throw exception
    }

    @Test
    public void testRemoveNonExistentFriends() {
        friendsDAO.removeFriends("NonExistent", "User");
    }
}