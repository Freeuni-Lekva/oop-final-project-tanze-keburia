import classes.quiz_utilities.options.Option;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OptionTest {
    @Test
    public void testSetGetPoints() {
        Option option = new Option("A", "1", "a", 1.0);
        assertEquals(1.0, option.getPoints(), 1e-6);
        option.setPoints(0.5);
        assertEquals(0.5, option.getPoints(), 1e-6);
    }
    @Test
    public void testSetGetAnswer() {
        Option option1 = new Option("A", "1", "a", 1.0);
        Option option = new Option(option1);
        assertEquals("a", option.getAnswer());
        option.setAnswer("1");
        assertEquals("1", option.getAnswer());
        assertEquals("a", option1.getAnswer());
    }
    @Test
    public void testGetQuestionID() {
        Option option = new Option("A", "1", "a", 1.0);
        assertEquals("A", option.getQuestionID());
    }
    @Test
    public void testGetOptionID() {
        Option option = new Option("A", "1", "a", 1.0);
        assertEquals("1", option.getOptionID());
    }
}
