package database;

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
        DatabaseConnector dbConnector = DatabaseConnector.getInstance();
        conn = dbConnector.getConnection();

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
    public void setup() {
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

    @Test
    public void testRemoveFriendRequest() {

        List<String> requests = requestDAO.getRequestList("Bob");
        assertEquals(2, requests.size());
        assertTrue(requests.contains("Alice"));
        assertTrue(requests.contains("Charlie"));

        requestDAO.removeRequest("Alice", "Bob");
        requests = requestDAO.getRequestList("Bob");
        assertEquals(1, requests.size());
        assertTrue(requests.contains("Charlie"));

        requestDAO.removeRequest("Charlie", "Bob");
        requests = requestDAO.getRequestList("Bob");
        assertEquals(0, requests.size());
        assertFalse(requests.contains("Charlie"));
    }
}