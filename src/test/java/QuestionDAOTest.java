import classes.Question;

import database.QuestionDAO;
import org.junit.*;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;


public class QuestionDAOTest {

    private static Connection conn;
    private QuestionDAO questionDAO;

    @BeforeClass
    public static void init() throws Exception {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metropolis_db", "root", "Akkdzidzi100!");
    }

    @Before
    public void clearTableAndInit() throws SQLException {
        questionDAO = new QuestionDAO(conn);

        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM questions");
        stmt.close();
    }


    @Test
    public void testAddGet() {
        questionDAO = new QuestionDAO(conn);
        Question q1 = new Question("What is the capital of Georgia?", "Tbilisi", "1", "1");
        Question q2 = new Question("Who has the most Ballon d'ors?", "Lionel Messi", "2", "1");
        Question q3 = new Question("Who was the youngest president of USA?", "Kennedy", "1", "2");

        questionDAO.addQuestion(q1);
        questionDAO.addQuestion(q2);
        questionDAO.addQuestion(q3);


        List<Question> result1 = questionDAO.getQuestions("1");
        List<Question> result2 = questionDAO.getQuestions("2");

        assertEquals(2, result1.size());
        assertEquals(1, result2.size());

        assertEquals(q1.getDescription(), result1.get(0).getDescription());
        assertEquals(q2.getDescription(), result1.get(1).getDescription());
        assertEquals(q3.getDescription(), result2.get(0).getDescription());


        assertEquals(q1.getAnswer(), result1.get(0).getAnswer());
        assertEquals(q2.getAnswer(), result1.get(1).getAnswer());
        assertEquals(q3.getAnswer(), result2.get(0).getAnswer());

    }

    @Test
    public void testEdit() throws SQLException {
        Question initial = new Question("Old Question", "Wrong answer", "5", "1" );
        questionDAO.addQuestion(initial);

        Question updated = new Question("New Question", "Correct answer", "5", "1" );
        questionDAO.editQuestion(updated);

        List<Question> result = questionDAO.getQuestions("1");

        assertEquals(1, result.size());
        assertEquals(updated.getDescription(), result.get(0).getDescription());
        assertEquals(updated.getAnswer(), result.get(0).getAnswer());
    }

    @Test
    public void testDelete() throws SQLException {
        Question question1 = new Question("Snikersi sjobs tu baunti?", "Snikersi", "2", "3" );
        questionDAO.addQuestion(question1);

        questionDAO.deleteQuestion(question1);

        List<Question> result1 = questionDAO.getQuestions("3");
        assertTrue(result1.isEmpty());

        Question question2 = new Question("Xinkali sjobs tu mwvadi?", "Xinkali", "3", "3");
        Question question3 = new Question("Vanilis nayini sjobs tu shokoladis?", "Shokoladis", "4", "3");

        questionDAO.addQuestion(question2);
        questionDAO.addQuestion(question3);

        List<Question> result2 = questionDAO.getQuestions("3");
        assertEquals(2, result2.size());

        questionDAO.deleteQuestion(question2);

        List<Question> result3 = questionDAO.getQuestions("3");
        assertEquals(1, result3.size());

    }

}
