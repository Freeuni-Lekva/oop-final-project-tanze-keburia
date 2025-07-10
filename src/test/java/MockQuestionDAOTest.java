import classes.quiz_utilities.questions.MockQuestion;
import classes.quiz_utilities.questions.Question;
import database.quiz_utilities.MockQuestionDAO;
import junit.framework.TestCase;
import org.junit.jupiter.api.BeforeEach;

public class MockQuestionDAOTest extends TestCase {
    private MockQuestionDAO mockQuestionDAO;
    @BeforeEach
    public void setUp(){
        mockQuestionDAO = new MockQuestionDAO(null);
        mockQuestionDAO.initialize();
        Question question = new MockQuestion("a", "a", "2" , "1");
        Question question1 = new MockQuestion("a" , "a", "3", "2");
        Question question2 = new MockQuestion("a", "a" ,"3", "3");
        mockQuestionDAO.addQuestion(question);
        mockQuestionDAO.addQuestion(question1);
        mockQuestionDAO.addQuestion(question2);
    }
    public void testAddQuestionAndGet() {

        assertEquals(3, mockQuestionDAO.getAllQuestions().size());
        assertEquals(2, mockQuestionDAO.getQuiz(
                "3"
        ).size());
    }
    public void testRemoveQuestionAndGet() {
        Question question = mockQuestionDAO.getQuestion("2");
        mockQuestionDAO.removeQuestion(question);
        assertEquals(0, mockQuestionDAO.getQuiz("1").size());
    }
    public void testModifyQuestion() {
        Question question =  mockQuestionDAO.getQuestion("2");
        question.setStatement("a");
        mockQuestionDAO.modifyQuestion(question);
        Question q3 = mockQuestionDAO.getQuestion("2");
        assertEquals("a", q3.getStatement());
    }
}
