import classes.social.Challenge;
import database.database_connection.DatabaseConnector;
import database.social.ChallengeDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ChallengeDAOTest {
    private static Connection conn;
    private ChallengeDAO challenges;
    @BeforeAll
    public static void setup() throws SQLException {
       conn = DatabaseConnector.getInstance().getConnection();
    }
    @BeforeEach
    public void init() throws SQLException {
        challenges = new ChallengeDAO(conn);
        challenges.initialize();
    }
    @Test
    public void testAddChallenge() throws SQLException {
        Challenge x = new Challenge("a", "b",  "1", "bobo", 5);
        challenges.addChallenge(x);
        Challenge y = new Challenge("b", "a",  "2", "bubu", 4);
        challenges.addChallenge(y);
        List<Challenge> challengesA = challenges.getUserChallenges("a");
        assertEquals(1, challengesA.size());
        assertEquals("bubu",  challengesA.get(0).getQuizName());
        assertEquals(4, challengesA.get(0).getScore(), 1e-6);
    }
    @Test
    public void testRemoveChallenge() throws SQLException {
        Challenge x = new Challenge("a", "b",  "1", "bobo", 5);
        challenges.addChallenge(x);
        Challenge y = new Challenge("b", "a",  "2", "bubu", 4);
        challenges.addChallenge(y);
        Challenge z = new Challenge("b", "a", "2", "bubu", 4);
        challenges.removeChallenge(z);
        List<Challenge> challengesA = challenges.getUserChallenges("a");
        assertEquals(0, challengesA.size());
        List<Challenge> challengesB = challenges.getUserChallenges("b");
        assertEquals(1, challengesB.size());
    }
    @Test
    public void testChallengeSent() throws SQLException {
        Challenge x = new Challenge("a", "b",  "1", "bobo", 5);
        assertFalse(challenges.ChallengeExists(x));
        challenges.addChallenge(x);
        Assertions.assertTrue(challenges.ChallengeExists(x));
    }

}
