package database;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;



public class FriendRequestDAOTest {
    private static DatabaseConnector dbConnector;
    private static FriendsDAO friendsDAO;
    private FriendRequestDAO requestDAO;

    @BeforeClass
    public static void setupDatabase() throws Exception {
        dbConnector = DatabaseConnector.getInstance(
                "jdbc:mysql://localhost:3306/mysql",
                "root",
                "Bozartma");

        try (Connection connection = dbConnector.getConnection();
             Statement stmt = connection.createStatement()) {
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

        friendsDAO = new FriendsDAO(dbConnector);
    }

    @Before
    public void setup() {
        requestDAO = new FriendRequestDAO(dbConnector, friendsDAO);
    }

    @Test
    public void testCreateFriendRequest()  {
        requestDAO.createRequest("Alice", "Bob");
        requestDAO.createRequest("Charlie", "Bob");

        List<String> requests = requestDAO.getRequestList("Bob");

        assertEquals(2, requests.size());

        assertTrue(requests.contains("Alice"));
        assertTrue(requests.contains("Charlie"));

    }

    @Test
    public void testRemoveFriendRequest()  {
        List<String> requests = requestDAO.getRequestList("Bob");

        assertEquals(2, requests.size());

        assertTrue(requests.contains("Alice"));
        assertTrue(requests.contains("Charlie"));

        requestDAO.deleteRequest("Alice", "Bob");
        requests = requestDAO.getRequestList("Bob");
        assertEquals(1, requests.size());
        assertTrue(requests.contains("Charlie"));

        requestDAO.deleteRequest("Charlie", "Bob");
        requests = requestDAO.getRequestList("Bob");
        assertEquals(0, requests.size());
        assertFalse(requests.contains("Charlie"));
    }



}