import classes.quiz_result.QuizResult;
import classes.quiz_utilities.quiz.Quiz;
import classes.quiz_utilities.quiz.RealQuiz;
import database.history.QuizHistoryDAO;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuizDAO;
import database.quiz_utilities.RealQuizDAO;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;

public class QuizHistoryDAOTest {
    private static Connection conn;
    private static QuizHistoryDAO historyDAO;
    private static QuizDAO quizDAO;

    @BeforeClass
    public static void setUpClass() throws Exception {
        conn = DatabaseConnector.getInstance().getConnection();
        quizDAO = new RealQuizDAO(conn);
        historyDAO = new QuizHistoryDAO(conn, quizDAO);
    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Before
    public void init() {
        historyDAO.initialize();
        quizDAO.initialize();
    }


    @Test
    public void testEmptyHistory() {
        List<QuizResult> results = historyDAO.getUserHistory("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNullResult() {
        historyDAO.addResult(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddResultWithNullUsername() {
        QuizResult result = new QuizResult(null, "2", "Quiz", 75, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryWithNullUsername() {
        historyDAO.getUserHistory(null);
    }

    @Test
    public void testGetHistoryByQuiz() throws SQLException {
        // Add quiz first
        Quiz quiz = new RealQuiz("creator", new Date(System.currentTimeMillis()), "quiz2", "Type", "Quiz Two", "Style");
        quizDAO.addQuiz(quiz);

        // Add results
        QuizResult result1 = new QuizResult("user1", "quiz2", "Quiz Two", 80, new Timestamp(System.currentTimeMillis()));
        QuizResult result2 = new QuizResult("user2", "quiz2", "Quiz Two", 90, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result1);
        historyDAO.addResult(result2);

        List<QuizResult> results = historyDAO.getResultsByQuiz("quiz2");
        assertEquals(2, results.size());
        // Should be ordered by score descending
        assertEquals("user2", results.get(0).getUsername());
        assertEquals(90, results.get(0).getScore(), 0.001);
        assertEquals("user1", results.get(1).getUsername());
    }

    @Test
    public void testGetResultsByQuizWithNonexistentQuiz() throws SQLException {
        List<QuizResult> results = historyDAO.getResultsByQuiz("nonexistent");
        assertTrue(results.isEmpty());
    }

    @Test
    public void testMaxScore() throws SQLException {
        // Add quiz first
        Quiz quiz = new RealQuiz("creator", new Date(System.currentTimeMillis()), "quiz3", "Type", "Quiz Three", "Style");
        quizDAO.addQuiz(quiz);

        // Test with no results
        assertEquals(0, historyDAO.getMaxResultForUser("user1", "quiz3"), 0.001);

        // Add results
        QuizResult result1 = new QuizResult("user1", "quiz3", "Quiz Three", 75, new Timestamp(System.currentTimeMillis()));
        QuizResult result2 = new QuizResult("user1", "quiz3", "Quiz Three", 85, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result1);
        historyDAO.addResult(result2);

        assertEquals(85, historyDAO.getMaxResultForUser("user1", "quiz3"), 0.001);
        assertEquals(0, historyDAO.getMaxResultForUser("user2", "quiz3"), 0.001);
    }

    @Test
    public void testGetUserAttemptCount() {
        // Add quiz first
        Quiz quiz = new RealQuiz("creator", new Date(System.currentTimeMillis()), "quiz4", "Type", "Quiz Four", "Style");
        quizDAO.addQuiz(quiz);

        assertEquals(0, historyDAO.getUserAttemptCount("user1"));

        // Add attempts
        QuizResult result1 = new QuizResult("user1", "quiz4", "Quiz Four", 70, new Timestamp(System.currentTimeMillis()));
        QuizResult result2 = new QuizResult("user1", "quiz4", "Quiz Four", 80, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result1);
        historyDAO.addResult(result2);

        assertEquals(2, historyDAO.getUserAttemptCount("user1"));
        assertEquals(0, historyDAO.getUserAttemptCount("user2"));
    }

    @Test
    public void testGetTopScoreForQuiz() {
        // Add quiz first
        Quiz quiz = new RealQuiz("creator", new Date(System.currentTimeMillis()), "quiz5", "Type", "Quiz Five", "Style");
        quizDAO.addQuiz(quiz);

        // Test with no results
        assertEquals(0.0, historyDAO.getTopScoreForQuiz("quiz5"), 0.001);

        // Add results
        QuizResult result1 = new QuizResult("user1", "quiz5", "Quiz Five", 65, new Timestamp(System.currentTimeMillis()));
        QuizResult result2 = new QuizResult("user2", "quiz5", "Quiz Five", 95, new Timestamp(System.currentTimeMillis()));
        QuizResult result3 = new QuizResult("user3", "quiz5", "Quiz Five", 85, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result1);
        historyDAO.addResult(result2);
        historyDAO.addResult(result3);

        assertEquals(95, historyDAO.getTopScoreForQuiz("quiz5"), 0.001);
    }



    @Test
    public void testInitializeTable() {
        // The initialize() method is called in @Before, so we can just verify it worked
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM quiz_history")) {
            // Just verifying the table exists and is empty
            assertFalse(rs.next());
        } catch (SQLException e) {
            fail("Table verification failed: " + e.getMessage());
        }
    }
}