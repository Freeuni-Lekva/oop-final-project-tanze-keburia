
import classes.quiz_utilities.quiz.Quiz;
import classes.quiz_utilities.quiz.RealQuiz;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.RealQuizDAO;
import org.junit.*;
import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class RealQuizDAOTest {
    private static Connection conn;
    private RealQuizDAO quizDAO;

    @BeforeClass
    public static void setupDatabase() throws SQLException {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        conn = dbc.getConnection();
        assert conn != null;
    }

    @Before
    public void setup() {
        quizDAO = new RealQuizDAO(conn);
        quizDAO.initialize();
    }

    @After
    public void cleanUp() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM quizzes");
        }
    }

    @Test
    public void addAndRemoveQuiz() {
        Date date = new Date();
        Quiz quiz = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "format");
        quiz.setNumQuestions(10);
        quiz.setTopic("General");
        quiz.setTimeLimit(30);
        quiz.setPageFormat("one-pager");

        quizDAO.addQuiz(quiz);
        List<Quiz> quizzes = quizDAO.getAll();
        assertEquals(1, quizzes.size());
        assertEquals(1, quizDAO.getNumQuizes());
        assertEquals("one-pager", quiz.getPageFormat());
        quizDAO.removeQuiz(quiz);
        quizzes = quizDAO.getAll();
        assertEquals(0, quizzes.size());
    }


    @Test
    public void testTopicAuthorType() {
        Date date = new Date();
        Quiz quiz = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "format");
        quiz.setNumQuestions(10);
        quiz.setTopic("General");
        quiz.setTimeLimit(30);
        quiz.setPageFormat("mefuri formati");
        quizDAO.addQuiz(quiz);
        List<Quiz> quizzes = quizDAO.getAllByType("multipleChoice");
        assertEquals(1, quizzes.size());
        assertEquals("mefuri formati", quiz.getPageFormat());
        quizzes = quizDAO.getAllbyTopic("General");
        assertEquals(1, quizzes.size());
        quizzes = quizDAO.getAllbyAuthor("Mzare");
        assertEquals(1, quizzes.size());
        quizDAO.removeQuiz(quiz);

    }

    @Test
    public void testModify(){
        Date date = new Date();
        Quiz quiz1 = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "Form");
        quiz1.setNumQuestions(10);
        quiz1.setTopic("General");
        quiz1.setTimeLimit(30);
        quizDAO.addQuiz(quiz1);

        Quiz quiz2 = new RealQuiz("Gio", date, "quiz1", "multipleChoice", "Gio's Quiz", "Form");
        quiz2.setNumQuestions(15);
        quiz2.setTopic("Math");
        quiz2.setTimeLimit(45);
        quizDAO.modifyQuiz(quiz2);

        Quiz modified = quizDAO.getQuiz("quiz1");
        assertEquals("Gio", modified.getAuthor());
        assertEquals("Gio's Quiz", modified.getName());
        assertEquals(15, modified.getNumQuestions());
        assertEquals("Math", modified.getTopic());
        assertEquals(45, modified.getTimeLimit());
    }



}