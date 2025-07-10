import classes.quiz_result.QuizResult;
import database.history.MockQuizHistoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MockQuizHistoryTest {
    private MockQuizHistoryDAO quizHistoryDAO;

    @BeforeEach
    public void setUp() {
        quizHistoryDAO = new MockQuizHistoryDAO(null);
        quizHistoryDAO.initialize();
    }

    @Test
    public void testAddAndGetQuizResult() {
        Timestamp submitTime = new Timestamp(System.currentTimeMillis());
       /* QuizResult quizResult = new QuizResult("mzare", "", 10, submitTime);
        quizHistoryDAO.AddQuizResult(quizResult);
        List<QuizResult> lst = quizHistoryDAO.getUserHistory("mzare");
        assertEquals(1, lst.size());
        assertEquals(quizResult, lst.get(0));*/
    }
}