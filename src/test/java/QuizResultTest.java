import classes.quiz_result.QuizResult;
import classes.quiz_utilities.quiz.MockQuiz;
import classes.quiz_utilities.quiz.Quiz;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

public class QuizResultTest {

    @Test
    public void testQuizResultFields() {
        Timestamp now = new Timestamp(System.currentTimeMillis());

        // Create a mock quiz
        Quiz quiz = new MockQuiz("user1", now, "101", "Multiple Choice", "Sample Quiz", "Page-by-page");
        quiz.setNumQuestions(10);
        quiz.setTopic("General Knowledge");

        QuizResult result = new QuizResult("user1", quiz, 85, now);

        assertEquals("user1", result.getUsername());
        assertEquals("101", result.getQuizId());
        assertEquals(85, result.getScore(), 0.001);  // For comparing double
        assertEquals(now, result.getSubmitTime());
    }
}
