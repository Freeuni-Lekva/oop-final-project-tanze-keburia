import classes.QuizResult;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

public class QuizResultTest {

    @Test
    public void testQuizResultFields() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
       /* QuizResult result = new QuizResult("user1", "101", 85, now);

        assertEquals("user1", result.getUsername());
        assertEquals("101", result.getQuizId());
        assertEquals(85, result.getScore());
        assertEquals(now, result.getSubmitTime());*/
    }
}
