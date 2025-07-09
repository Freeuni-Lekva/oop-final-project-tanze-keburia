import classes.Challenge;
import classes.QuizResult;
import classes.quiz_utilities.Quiz;
import classes.quiz_utilities.RealQuiz;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChallengeTest {
    @Test
    public void test() {
        //Quiz quiz = new RealQuiz("x", null, "1", "Text", "test", "one-pager") ;

        Challenge challenge = new Challenge("a", "b", "A", "1", "bobo", 1);
        assertEquals("b", challenge.getReceiver());
        assertEquals("a", challenge.getSender());
        assertEquals(1, challenge.getScore(), 1e-6);
        assertEquals("1", challenge.getQuizID());
        assertEquals("A", challenge.getId());
        assertEquals("bobo", challenge.getQuizName());
    }
}
