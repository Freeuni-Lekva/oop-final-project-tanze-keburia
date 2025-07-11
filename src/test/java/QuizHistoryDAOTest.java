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
import org.junit.jupiter.api.BeforeEach;

import java.sql.*;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuizHistoryDAOTest {
    private static Connection conn;
    private static QuizHistoryDAO historyDAO;
    private static QuizDAO quizDAO;

    @BeforeClass
    public static void setUpClass() throws Exception {
        conn = DatabaseConnector.getInstance().getConnection();
        quizDAO = new RealQuizDAO(conn);
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

//    @Before
//    public void clearTable() throws SQLException {
//        try (Statement stmt = conn.createStatement()) {
//            stmt.execute("DELETE FROM quiz_history");
//            stmt.execute("DELETE FROM quizzes");
//        }
//    }

    @Test
    public void testAddAndRetrieveResult() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Create mock quiz
        Quiz quiz = new MockQuiz("alice", now, "1", "Multiple Choice", "Sample Quiz", "Page-by-page");
        quiz.setNumQuestions(10);
        quiz.setTopic("Science");

        QuizResult result = new QuizResult("alice", quiz, 95, now);
        historyDAO.addResult(result);

        List<QuizResult> results = historyDAO.getUserHistory("alice");
        assertEquals(1, results.size());

        QuizResult stored = results.get(0);
        assertEquals("alice", stored.getUsername());
        assertEquals("1", stored.getQuizId());
        assertEquals(95, stored.getScore(), 0.001);  // delta added for comparing doubles
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
        Quiz quiz = new MockQuiz("bob", new Timestamp(System.currentTimeMillis()), "2", "MCQ", "Quiz 2", "All-in-one");
        QuizResult result = new QuizResult(null, quiz, 75, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryWithNullUsername() {
        historyDAO.getUserHistory(null);
    }
    @Test
    public void testGetHistoryByQuiz() throws SQLException {

        QuizResult  result = new QuizResult("alice", "1", "ab", 95, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result);
        Quiz take = new RealQuiz("ld", new Date(System.currentTimeMillis()), "1", "text", "ab", "one-page");
        quizDAO.addQuiz(take);
        QuizResult result1 = new QuizResult("bob", "1", "ab", 100, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result1);
        List<QuizResult> results = historyDAO.getResultsByQuiz("1");
        assertEquals(2, results.size());
        assertEquals("bob", results.get(0).getUsername());
    }

    @Test
    public void testMaxScore() throws SQLException {
        assertEquals(0, historyDAO.getResultsByQuiz("1").size());
        QuizResult result = new QuizResult("alice", "1", "ab", 105, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result);
        assertEquals(105, historyDAO.getMaxResultForUser("alice", "1"), 1e-6);
        assertEquals(0, historyDAO.getMaxResultForUser("bob", "1"), 1e-6);
    }
}
