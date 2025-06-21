import classes.MockQuestion;
import classes.Question;
import database.MockQuizDAO;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class MockQuizDAOTest extends TestCase {
   private MockQuizDAO mockQuizDAO;
    public void testAddQuestion() {
        mockQuizDAO = new MockQuizDAO();
        Question question = new MockQuestion(2, 1);
    }
}
