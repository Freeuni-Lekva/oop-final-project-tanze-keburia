import classes.QuizResult;
import database.DatabaseConnectionPull;
import database.database_connection.DatabaseConnectionPool;
import database.database_connection.DatabaseConnector;
import database.QuizHistoryDAO;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuizDAO;
import org.junit.*;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class QuizHistoryDAOTest {
    private static Connection conn;
    private static QuizHistoryDAO historyDAO;

    @BeforeClass
    public static void setUpClass() throws Exception {
        Connection conn = DatabaseConnector.getInstance().getConnection();
        QuizDAO quizDAO = new RealQuizDAO(conn);
        historyDAO = new QuizHistoryDAO(conn, quizDAO);
        historyDAO.initialize();
        quizDAO.initialize();
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Before
    public void clearTable() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM quiz_history");
            stmt.execute("DELETE FROM quizzes");
        }
    }

    @Test
    public void testAddAndRetrieveResult() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        /*QuizResult result = new QuizResult("alice", "1", 95, now);
        historyDAO.addResult(result);

        List<QuizResult> results = historyDAO.getUserHistory("alice");
        assertEquals(1, results.size());

        QuizResult stored = results.get(0);
        assertEquals("alice", stored.getUsername());
        assertEquals("1", stored.getQuizId());
        assertEquals(95, stored.getScore());*/
    }

    @Test
    public void testEmptyHistory() {
        List<QuizResult> results = historyDAO.getUserHistory("ghost");
        assertTrue(results.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullResult() {
        historyDAO.addResult(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddResultWithNullUsername() {
       // QuizResult result = new QuizResult(null, "2", 75, new Timestamp(System.currentTimeMillis()));
       // historyDAO.addResult(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryWithNullUsername() {
        historyDAO.getUserHistory(null);
    }
}
