import classes.quiz_result.QuizResult;
import classes.quiz_utilities.quiz.MockQuiz;
import classes.quiz_utilities.quiz.Quiz;
import database.database_connection.DatabaseConnector;
import database.history.QuizHistoryDAO;
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
        conn = DatabaseConnector.getInstance().getConnection();
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

        // Create mock quiz
        Quiz quiz = new MockQuiz("alice", now, "1", "Multiple Choice", "Sample Quiz", "Page-by-page");
        quiz.setNumQuestions(10);
        quiz.setTopic("Science");

        // Add quiz to DB using RealQuizDAO
        QuizDAO quizDAO = new RealQuizDAO(conn); // new instance or use the one from @BeforeClass
        quizDAO.addQuiz(quiz); // 🔥 THIS is the missing part

        QuizResult result = new QuizResult("alice", quiz, 95, now);
        historyDAO.addResult(result);

        List<QuizResult> results = historyDAO.getUserHistory("alice");
        assertEquals(1, results.size());

        QuizResult stored = results.get(0);
        assertEquals("alice", stored.getUsername());
        assertEquals("1", stored.getQuizId());
        assertEquals(95, stored.getScore(), 0.001);
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

    @Test(expected = RuntimeException.class)
    public void testGetUserHistorySQLException() throws SQLException {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();  // force SQLException

        QuizDAO dummyQuizDAO = new RealQuizDAO(badConn);
        QuizHistoryDAO faultyDAO = new QuizHistoryDAO(badConn, dummyQuizDAO);

        faultyDAO.getUserHistory("alice"); // should throw RuntimeException
    }


    @Test(expected = RuntimeException.class)
    public void testInitializeSQLException() throws SQLException {
        Connection brokenConn = DatabaseConnector.getInstance().getConnection();
        brokenConn.close(); // this breaks the connection
        QuizDAO quizDAO = new RealQuizDAO(brokenConn);
        QuizHistoryDAO faultyDAO = new QuizHistoryDAO(brokenConn, quizDAO);

        faultyDAO.initialize(); // should throw RuntimeException
    }

    @Test(expected = RuntimeException.class)
    public void testAddResultSQLException() throws SQLException {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();  // force SQLException

        QuizDAO dummyQuizDAO = new RealQuizDAO(badConn);
        QuizHistoryDAO faultyDAO = new QuizHistoryDAO(badConn, dummyQuizDAO);

        Quiz quiz = new MockQuiz("alice", new Timestamp(System.currentTimeMillis()), "1", "MCQ", "Title", "Mode");
        QuizResult result = new QuizResult("alice", quiz, 80, new Timestamp(System.currentTimeMillis()));

        faultyDAO.addResult(result); // should throw RuntimeException
    }
}

