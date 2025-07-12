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

    }

    @AfterClass
    public static void tearDownClass() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // @Before
   /* public void clearTable() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS quiz_history");
            stmt.execute("DROP TABLE IF EXISTS quizzes");
        }
    }*/

    @Before
    public void init() {
        historyDAO.initialize();
        quizDAO.initialize();
    }

    @Test
    public void testAddAndRetrieveResult() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        Quiz quiz = new MockQuiz("alice", now, "1", "Multiple Choice", "Sample Quiz", "Page-by-page");
        quiz.setNumQuestions(10);
        quiz.setTopic("Science");

        RealQuizDAO quizDAO = new RealQuizDAO(conn);
        quizDAO.addQuiz(quiz);

        QuizResult result = new QuizResult("alice", quiz, 95, now);

        //QuizResult result = new QuizResult("alice", "1", "ab", 95, now);
      
        historyDAO.addResult(result);
        quizDAO.addQuiz(new RealQuiz("me", now, "1", "text", "ab", "one-page"));
        List<QuizResult> results = historyDAO.getUserHistory("alice");
        assertEquals(1, results.size());

        QuizResult stored = results.get(0);
        assertEquals("alice", stored.getUsername());
        System.out.println(stored.getQuizId());
        assertEquals("1", stored.getQuizId());

        assertEquals(95, stored.getScore(), 0.001);

        //assertEquals(95, stored.getScore(), 1e-6);

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
        QuizResult result = new QuizResult(null, "2", "bo", 75, new Timestamp(System.currentTimeMillis()));
        historyDAO.addResult(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetHistoryWithNullUsername() {
        historyDAO.getUserHistory(null);
    }



//    @Test
//    public void testGetRecentlyPlayedQuizzes() throws SQLException {
//        Timestamp now = new Timestamp(System.currentTimeMillis());
//
//        Quiz quiz1 = new MockQuiz("user1", now, "quiz1", "MCQ", "Quiz 1", "format");
//        quiz1.setNumQuestions(5);
//        quiz1.setTopic("Math");
//
//        Quiz quiz2 = new MockQuiz("user1", new Timestamp(now.getTime() + 1000), "quiz2", "MCQ", "Quiz 2", "format");
//        quiz2.setNumQuestions(5);
//        quiz2.setTopic("Science");
//
//        Quiz quiz3 = new MockQuiz("user1", new Timestamp(now.getTime() + 2000), "quiz3", "MCQ", "Quiz 3", "format");
//        quiz3.setNumQuestions(5);
//        quiz3.setTopic("History");
//
//        QuizDAO quizDAO = new RealQuizDAO(conn);
//        quizDAO.addQuiz(quiz1);
//        quizDAO.addQuiz(quiz2);
//        quizDAO.addQuiz(quiz3);
//
//        historyDAO.addResult(new QuizResult("tester", quiz1, 80, new Timestamp(now.getTime() + 3000)));
//        historyDAO.addResult(new QuizResult("tester", quiz2, 85, new Timestamp(now.getTime() + 4000)));
//        historyDAO.addResult(new QuizResult("tester", quiz3, 90, new Timestamp(now.getTime() + 5000)));
//
//        List<Quiz> recentPlayed = historyDAO.getRecentlyPlayedQuizzes("tester", 2);
//
//        assertEquals(2, recentPlayed.size());
//        assertEquals("quiz3", recentPlayed.get(0).getID());
//        assertEquals("quiz2", recentPlayed.get(1).getID());
//
//        quizDAO.removeQuiz(quiz1);
//        quizDAO.removeQuiz(quiz2);
//        quizDAO.removeQuiz(quiz3);
//    }


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
