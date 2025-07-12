

import database.database_connection.DatabaseConnector;
import database.social.FriendsDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class FriendsDAOTest {
    private static Connection conn;
    private FriendsDAO friendsDAO;

    @BeforeClass
    public static void setUpClass() throws Exception {
        conn = DatabaseConnector.getInstance().getConnection();
    }

    @Before
    public void setUp() throws Exception {
        friendsDAO = new FriendsDAO(conn);
        friendsDAO.initialize(); // Clean slate for each test
    }

    @After
    public void tearDown() throws Exception {
        friendsDAO.initialize(); // Clean up after each test
    }

    @Test
    public void testInitialize() {
        // Tested implicitly by setUp()
        assertTrue(true);
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
        friendsDAO.addFriends("Bob", "Alice"); // Reverse order

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        List<String> bobFriends = friendsDAO.getFriends("Bob");

        assertTrue(aliceFriends.contains("Bob"));
        assertTrue(bobFriends.contains("Alice"));
        assertEquals(1, aliceFriends.size());
        assertEquals(1, bobFriends.size());
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

    @Test(expected = RuntimeException.class)
    public void testAddDuplicateFriends() {
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.addFriends("Alice", "Bob"); // Should throw
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
        friendsDAO.removeFriends("Bob", "Alice"); // Reverse order

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        List<String> bobFriends = friendsDAO.getFriends("Bob");

        assertFalse(aliceFriends.contains("Bob"));
        assertFalse(bobFriends.contains("Alice"));
        assertEquals(0, aliceFriends.size());
        assertEquals(0, bobFriends.size());
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

    @Test
    public void testRemoveNonExistentFriendship() {
        // Should not throw, just do nothing
        friendsDAO.removeFriends("Alice", "Bob");
    }

    @Test
    public void testGetFriends() {
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.addFriends("Alice", "Charlie");

        List<String> aliceFriends = friendsDAO.getFriends("Alice");
        List<String> bobFriends = friendsDAO.getFriends("Bob");
        List<String> charlieFriends = friendsDAO.getFriends("Charlie");

        assertEquals(2, aliceFriends.size());
        assertTrue(aliceFriends.contains("Bob"));
        assertTrue(aliceFriends.contains("Charlie"));

        assertEquals(1, bobFriends.size());
        assertTrue(bobFriends.contains("Alice"));

        assertEquals(1, charlieFriends.size());
        assertTrue(charlieFriends.contains("Alice"));
    }

    @Test
    public void testGetFriendsEmpty() {
        List<String> friends = friendsDAO.getFriends("Alice");
        assertTrue(friends.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFriendsWithNullUsername() {
        friendsDAO.getFriends(null);
    }

    @Test
    public void testMultipleOperations() {
        // Test sequence of operations
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.addFriends("Alice", "Charlie");
        friendsDAO.addFriends("Bob", "David");

        // Verify initial state
        assertEquals(2, friendsDAO.getFriends("Alice").size());
        assertEquals(2, friendsDAO.getFriends("Bob").size());
        assertEquals(1, friendsDAO.getFriends("Charlie").size());
        assertEquals(1, friendsDAO.getFriends("David").size());

        // Remove some friendships
        friendsDAO.removeFriends("Alice", "Charlie");
        friendsDAO.removeFriends("Bob", "David");

        // Verify updated state
        assertEquals(1, friendsDAO.getFriends("Alice").size());
        assertEquals(1, friendsDAO.getFriends("Bob").size());
        assertEquals(0, friendsDAO.getFriends("Charlie").size());
        assertEquals(0, friendsDAO.getFriends("David").size());
    }

    @Test
    public void testFriendshipStorageOrder() {
        // Verify that friendships are always stored with user_a < user_b
        friendsDAO.addFriends("Bob", "Alice"); // Intentionally reverse order

        try (var stmt = conn.createStatement();
             var rs = stmt.executeQuery("SELECT user_a, user_b FROM friends")) {
            assertTrue(rs.next());
            assertEquals("Alice", rs.getString("user_a"));
            assertEquals("Bob", rs.getString("user_b"));
            assertFalse(rs.next());
        } catch (SQLException e) {
            fail("Database query failed: " + e.getMessage());
        }
    }
    @Test
    public void testRemoveUser() throws SQLException {
        friendsDAO.initialize();
        friendsDAO.addFriends("Alice", "Bob");
        friendsDAO.removeUser("Bob");
        List<String>friends = friendsDAO.getFriends("Alice");
        assertEquals(0, friends.size());
    }
}