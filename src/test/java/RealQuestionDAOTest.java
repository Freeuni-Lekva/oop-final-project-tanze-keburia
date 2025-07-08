import classes.quiz_utilities.Question;
import classes.quiz_utilities.RealQuestion;

import database.database_connection.DatabaseConnectionPool;
import database.quiz_utilities.RealQuestionDAO;
import org.junit.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class RealQuestionDAOTest {

    private static Connection conn;
    private RealQuestionDAO questionDAO;

    @BeforeClass
    public static void init() throws Exception {
        conn = DriverManager.getConnection(DatabaseConnectionPool.getUrl(), DatabaseConnectionPool.getUserName(), DatabaseConnectionPool.getPassword());
    }

    @Before
    public void clearTableAndInit() throws SQLException {
        questionDAO = new RealQuestionDAO(conn);

        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM questions");
        stmt.close();
    }


    @Test
    public void testAddGet() {
        questionDAO = new RealQuestionDAO(conn);
        Question q1 = new RealQuestion("What is the capital of Georgia?", "Tbilisi", "1", "1", "1.0");
        Question q2 = new RealQuestion("Who has the most Ballon d'ors?", "Lionel Messi", "2", "1", "1.0");
        Question q3 = new RealQuestion("Who was the youngest president of USA?", "Kennedy", "1", "2", "2.0");

        questionDAO.addQuestion(q1);
        questionDAO.addQuestion(q2);
        questionDAO.addQuestion(q3);

        List<Question> result1 = questionDAO.getQuiz("1");
        List<Question> result2 = questionDAO.getQuiz("2");

        assertEquals(2, result1.size());
        assertEquals(1, result2.size());

        assertEquals(q1.getStatement(), result1.get(0).getStatement());
        assertEquals(q2.getStatement(), result1.get(1).getStatement());
        assertEquals(q3.getStatement(), result2.get(0).getStatement());

        assertEquals(q1.getAnswer(), result1.get(0).getAnswer());
        assertEquals(q2.getAnswer(), result1.get(1).getAnswer());
        assertEquals(q3.getAnswer(), result2.get(0).getAnswer());

    }

    @Test
    public void testEdit() throws SQLException {
        Question initial = new RealQuestion("Old Question", "Wrong answer", "5", "1", "1.0");
        questionDAO.addQuestion(initial);

        Question updated = new RealQuestion("New Question", "Correct answer", "5", "1", "2.5");
        questionDAO.modifyQuestion(updated);

        List<Question> result = questionDAO.getQuiz("1");

        assertEquals(1, result.size());
        System.out.println(result.get(0).getStatement());
        System.out.println(result.get(0).getAnswer());
        assertEquals(updated.getStatement(), result.get(0).getStatement());
        assertEquals(updated.getAnswer(), result.get(0).getAnswer());
    }

    @Test
    public void testDelete() throws SQLException {
        Question question1 = new RealQuestion("Snikersi sjobs tu baunti?", "Snikersi", "2", "3", "1.0");
        questionDAO.addQuestion(question1);

        questionDAO.removeQuestion(question1);

        List<Question> result1 = questionDAO.getQuiz("3");
        assertTrue(result1.isEmpty());

        RealQuestion question2 = new RealQuestion("Xinkali sjobs tu mwvadi?", "Xinkali", "3", "3", "10.0");
        RealQuestion question3 = new RealQuestion("Vanilis nayini sjobs tu shokoladis?", "Shokoladis", "4", "3", "5.0");

        questionDAO.addQuestion(question2);
        questionDAO.addQuestion(question3);

        List<Question> result2 = questionDAO.getQuiz("3");
        assertEquals(2, result2.size());

        questionDAO.removeQuestion(question2);

        List<Question> result3 = questionDAO.getQuiz("3");
        assertEquals(1, result3.size());

    }

    @Test
    public void testGetAllQuestions() throws SQLException {
        Question question1 = new RealQuestion("Snikersi sjobs tu baunti?", "Snikersi", "2", "1", "1.0");
        RealQuestion question2 = new RealQuestion("Xinkali sjobs tu mwvadi?", "Xinkali", "3", "2", "10.0");
        RealQuestion question3 = new RealQuestion("Vanilis nayini sjobs tu shokoladis?", "Shokoladis", "3", "3", "5.0");

        questionDAO.addQuestion(question1);
        questionDAO.addQuestion(question2);
        questionDAO.addQuestion(question3);

        List<Question> correctResult = new ArrayList<>();
        correctResult.add(question1);
        correctResult.add(question2);
        correctResult.add(question3);

        List<Question> daoRes = questionDAO.getAllQuestions();

        assertEquals(daoRes, correctResult);

    }


    @Test
    public void testGetQuestion() throws SQLException {
        Question input = new RealQuestion("2 + 2 = ?", "4", "69", "102", "1.0");
        questionDAO.addQuestion(input);

        Question result = questionDAO.getQuestion("69");

        assert(result.equals(input));
    }

}
