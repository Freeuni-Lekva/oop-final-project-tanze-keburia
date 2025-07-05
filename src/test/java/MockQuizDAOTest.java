import database.quiz_utilities.MockQuizDAO;
import junit.framework.TestCase;

public class MockQuizDAOTest extends TestCase {
   private MockQuizDAO mockQuizDAO;
    public void testAddQuestion() {
        mockQuizDAO = new MockQuizDAO(null);
       // Question question = new MockQuestion(2, 1);
    }
}
