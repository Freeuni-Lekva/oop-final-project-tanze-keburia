

import database.database_connection.DatabaseConnector;
import database.social.FriendRequestDAO;
import database.social.FriendsDAO;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class FriendRequestDAOTest {
    private static Connection conn;
    private static FriendsDAO friendsDAO;
    private FriendRequestDAO requestDAO;

    @BeforeClass
    public static void setupDatabase() throws Exception {

        DatabaseConnector dbc = DatabaseConnector.getInstance();
        conn = dbc.getConnection();


        // Set up test tables
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS friends");
            stmt.execute("DROP TABLE IF EXISTS requests");

            stmt.execute("CREATE TABLE friends (" +
                    "user_a VARCHAR(255), " +
                    "user_b VARCHAR(255), " +
                    "PRIMARY KEY (user_a, user_b), " +
                    "CHECK (user_a < user_b))");

            stmt.execute("CREATE TABLE requests (" +
                    "sender VARCHAR(255) NOT NULL, " +
                    "receiver VARCHAR(255) NOT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "PRIMARY KEY (sender, receiver), " +
                    "CHECK (sender <> receiver))");
        }

        friendsDAO = new FriendsDAO(conn);
    }

    @Before
    public void setup() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM friends");
            stmt.execute("DELETE FROM requests");
        }

        requestDAO = new FriendRequestDAO(conn, friendsDAO);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    public void testCreateFriendRequest() {

        requestDAO.createRequest("Alice", "Bob");
        requestDAO.createRequest("Charlie", "Bob");

        List<String> requests = requestDAO.getRequestList("Bob");

        assertEquals(2, requests.size());
        assertTrue(requests.contains("Alice"));
        assertTrue(requests.contains("Charlie"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestWithNullSender() {
        requestDAO.createRequest(null, "Bob");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestWithNullReceiver() {
        requestDAO.createRequest("Alice", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateRequestWithSameSenderAndReceiver() {
        requestDAO.createRequest("Alice", "Alice");
    }

    @Test
    public void testGetEmptyRequestList() {
        List<String> requests = requestDAO.getRequestList("NonExistentUser");
        assertTrue(requests.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveRequestWithNullSender() {
        requestDAO.removeRequest(null, "Bob");
    }

    @Test
    public void testInitialize() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS requests");
        }
        try (Statement stmt = conn.createStatement()) {
            stmt.executeQuery("SELECT * FROM requests");
            fail("Expected SQLException");
        } catch (SQLException e) {
        }

        requestDAO.initialize();

        try (Statement stmt = conn.createStatement()) {
            stmt.executeQuery("SELECT * FROM requests");
        }
    }

    @Test
    public void testRemoveRequest() {
        requestDAO.createRequest("Alice", "Bob");
        assertEquals(1, requestDAO.getRequestList("Bob").size());
        requestDAO.removeRequest("Alice", "Bob");
        assertEquals(0, requestDAO.getRequestList("Alice").size());
    }

}