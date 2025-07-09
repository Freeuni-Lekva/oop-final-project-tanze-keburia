import classes.QuizResult;
import database.MockQuizHistoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MockQuizHistoryTest {
    private MockQuizHistoryDAO quizHistoryDAO;
    private QuizResult quizResult1;
    private QuizResult quizResult2;
    private QuizResult quizResult3;

    @BeforeEach
    public void setUp() {
        quizHistoryDAO = new MockQuizHistoryDAO(null);
        quizHistoryDAO.initialize();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        quizResult1 = new QuizResult("user1", "quiz1", 85, now);
        quizResult2 = new QuizResult("user1", "quiz2", 90, new Timestamp(now.getTime() + 1000));
        quizResult3 = new QuizResult("user2", "quiz1", 75, new Timestamp(now.getTime() + 2000));
    }


    @Test
    public void testAddAndGetSingleQuizResult() {
        quizHistoryDAO.AddQuizResult(quizResult1);

        List<QuizResult> results = quizHistoryDAO.getUserHistory("user1");
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(quizResult1, results.get(0));
    }

    @Test
    public void testAddMultipleResultsForSameUser() {
        quizHistoryDAO.AddQuizResult(quizResult1);
        quizHistoryDAO.AddQuizResult(quizResult2);

        List<QuizResult> results = quizHistoryDAO.getUserHistory("user1");
        assertEquals(2, results.size());
        assertTrue(results.contains(quizResult1));
        assertTrue(results.contains(quizResult2));
    }



}