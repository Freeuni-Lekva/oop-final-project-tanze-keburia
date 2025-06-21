import classes.MockQuestion;
import classes.Question;
import database.MockQuestionDAO;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.BeforeClass;

public class MockQuestionDAOTest extends TestCase {
    private MockQuestionDAO mockQuestionDAO;
    public void testAddQuestionAndGet() {
        mockQuestionDAO = new MockQuestionDAO(null);
        mockQuestionDAO.initialize();
        Question question = new MockQuestion(2 , 1);
        Question question1 = new MockQuestion(3, 2);
        Question question2 = new MockQuestion(3, 3);
        mockQuestionDAO.addQuestion(question);
        mockQuestionDAO.addQuestion(question1);
        mockQuestionDAO.addQuestion(question2);
        assertEquals(3, mockQuestionDAO.getAllQuestions().size());
        assertEquals(2, mockQuestionDAO.getQuiz(
                3
        ).size());
    }
    public void testRemoveQuestionAndGet() {
        mockQuestionDAO = new MockQuestionDAO(null);
        mockQuestionDAO.initialize();
        Question question = new MockQuestion(2 , 1);
        Question question1 = new MockQuestion(3, 2);
        Question question2 = new MockQuestion(3, 3);
        mockQuestionDAO.addQuestion(question);
        mockQuestionDAO.addQuestion(question1);
        mockQuestionDAO.addQuestion(question2);
        mockQuestionDAO.removeQuestion(question);
        assertEquals(0, mockQuestionDAO.getQuiz(1).size());
    }
    public void testModifyQuestion() {
        mockQuestionDAO = new MockQuestionDAO(null);
        mockQuestionDAO.initialize();
        Question question = new MockQuestion(2 , 1);
        Question question1 = new MockQuestion(3, 2);
        Question question2 = new MockQuestion(3, 3);
        mockQuestionDAO.addQuestion(question);
        mockQuestionDAO.addQuestion(question1);
        mockQuestionDAO.addQuestion(question2);
        question.setStatement("a");
        mockQuestionDAO.modifyQuestion(question);
        Question q3 = mockQuestionDAO.getQuestion(1);
        assertEquals("a", q3.getStatement());
    }
}
