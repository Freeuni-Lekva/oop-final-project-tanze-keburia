import classes.RealQuiz;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;

public class RealQuizTest extends TestCase {

    @Test
    public void testQuiz(){
        Date date = new Date(System.currentTimeMillis());
        RealQuiz quiz = new RealQuiz("Mzare", date, "0", "multipleChoise", "Mzare's Quiz");
        assertEquals("Mzare's Quiz", quiz.getName());
        assertEquals("0", quiz.getID());
        assertEquals("Mzare", quiz.getAuthor());
        assertEquals(date, quiz.getCreationDate());
        assertEquals("multipleChoise", quiz.getType());
        quiz.setNumQuestions(2);
        assertEquals(2, quiz.getNumQuestions());
        quiz.setTopic("math");
        assertEquals("math", quiz.getTopic());
        quiz.setVisible(true);
        quiz.setTimeLimit(1000);
        assertEquals(1000, quiz.getTimeLimit());
        quiz.setName("Tarash's Quiz");
        assertEquals("Tarash's Quiz", quiz.getName());

    }
}
