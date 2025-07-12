import database.database_connection.DatabaseConnector;
import database.social.FriendsDAO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class FriendsDAOTest {

    private static Connection conn;
    private FriendsDAO friendsDAO;

    @BeforeClass
    public static void setUpClass() throws Exception {

        conn = DatabaseConnector.getInstance(

        ).getConnection();


        assert(conn != null);
        // Create users table
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
    public void setUp(){
        friendsDAO = new FriendsDAO(conn);
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

    void testRemoveUser() {

    }

}