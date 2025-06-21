import classes.RealQuestion;

import database.RealQuestionDAO;
import org.junit.*;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;


public class RealQuestionDAOTest {

    private static Connection conn;
    private RealQuestionDAO questionDAO;

    @BeforeClass
    public static void init() throws Exception {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/metropolis_db", "root", "Akkdzidzi100!");
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
        RealQuestion q1 = new RealQuestion("What is the capital of Georgia?", "Tbilisi", "1", "1");
        RealQuestion q2 = new RealQuestion("Who has the most Ballon d'ors?", "Lionel Messi", "2", "1");
        RealQuestion q3 = new RealQuestion("Who was the youngest president of USA?", "Kennedy", "1", "2");

        questionDAO.addQuestion(q1);
        questionDAO.addQuestion(q2);
        questionDAO.addQuestion(q3);


        List<RealQuestion> result1 = questionDAO.getQuestions("1");
        List<RealQuestion> result2 = questionDAO.getQuestions("2");

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
        RealQuestion initial = new RealQuestion("Old Question", "Wrong answer", "5", "1" );
        questionDAO.addQuestion(initial);

        RealQuestion updated = new RealQuestion("New Question", "Correct answer", "5", "1" );
        questionDAO.editQuestion(updated);

        List<RealQuestion> result = questionDAO.getQuestions("1");

        assertEquals(1, result.size());
        assertEquals(updated.getDescription(), result.get(0).getDescription());
        assertEquals(updated.getAnswer(), result.get(0).getAnswer());
    }

    @Test
    public void testDelete() throws SQLException {
        RealQuestion question1 = new RealQuestion("Snikersi sjobs tu baunti?", "Snikersi", "2", "3" );
        questionDAO.addQuestion(question1);

        questionDAO.deleteQuestion(question1);

        List<RealQuestion> result1 = questionDAO.getQuestions("3");
        assertTrue(result1.isEmpty());

        RealQuestion question2 = new RealQuestion("Xinkali sjobs tu mwvadi?", "Xinkali", "3", "3");
        RealQuestion question3 = new RealQuestion("Vanilis nayini sjobs tu shokoladis?", "Shokoladis", "4", "3");

        questionDAO.addQuestion(question2);
        questionDAO.addQuestion(question3);

        List<RealQuestion> result2 = questionDAO.getQuestions("3");
        assertEquals(2, result2.size());

        questionDAO.deleteQuestion(question2);

        List<RealQuestion> result3 = questionDAO.getQuestions("3");
        assertEquals(1, result3.size());

    }

}
