import classes.quiz_result.QuizResult;
import classes.quiz_utilities.quiz.Quiz;
import classes.quiz_utilities.quiz.RealQuiz;
import database.history.MockQuizHistoryDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Date;
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
        Quiz dummyQuiz = new RealQuiz(
                "test_author",
                new Date(),
                "quiz123",
                "MULTIPLE_CHOICE",
                "Sample Quiz",
                "SINGLE_PAGE"
        );

        QuizResult quizResult = new QuizResult("mzare", dummyQuiz, 10, submitTime);
        quizHistoryDAO.AddQuizResult(quizResult);
        List<QuizResult> lst = quizHistoryDAO.getUserHistory("mzare");
        assertEquals(1, lst.size());
        assertEquals(quizResult, lst.get(0));
    }
}