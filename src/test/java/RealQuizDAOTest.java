import classes.quiz_utilities.*;
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
        assertNotNull(conn);
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
    public void testAddAndRemoveQuiz() {
        Date date = new Date();
        Quiz quiz = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "one-pager");
        quiz.setNumQuestions(10);
        quiz.setTopic("General");
        quiz.setTimeLimit(30);

        quizDAO.addQuiz(quiz);
        List<Quiz> quizzes = quizDAO.getAll();
        assertEquals(1, quizzes.size());
        assertEquals(1, quizDAO.getNumQuizes());
        assertEquals("one-pager", quizzes.get(0).getPageFormat());

        quizDAO.removeQuiz(quiz);
        quizzes = quizDAO.getAll();
        assertEquals(0, quizzes.size());
        assertEquals(0, quizDAO.getNumQuizes());
    }

    @Test
    public void testGetQuiz() {
        Date date = new Date();
        Quiz quiz = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "format");
        quiz.setNumQuestions(10);
        quiz.setTopic("General");
        quiz.setTimeLimit(30);
        quizDAO.addQuiz(quiz);

        Quiz retrieved = quizDAO.getQuiz("quiz1");
        assertNotNull(retrieved);
        assertEquals("quiz1", retrieved.getID());
        assertEquals("Mzare's Quiz", retrieved.getName());
        assertEquals("Mzare", retrieved.getAuthor());
        assertEquals(10, retrieved.getNumQuestions());
        assertEquals("General", retrieved.getTopic());
        assertEquals(30, retrieved.getTimeLimit());
    }

    @Test
    public void testGetNonExistentQuiz() {
        assertNull(quizDAO.getQuiz("nonexistent"));
    }

    @Test
    public void testGetQuizNameById() {
        Date date = new Date();
        Quiz quiz = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "format");
        quizDAO.addQuiz(quiz);

        assertEquals("Mzare's Quiz", quizDAO.getQuizNameById("quiz1"));
        assertNull(quizDAO.getQuizNameById("nonexistent"));
    }

    @Test
    public void testTopicAuthorTypeFilters() {
        Date date = new Date();
        Quiz quiz1 = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "format");
        quiz1.setNumQuestions(10);
        quiz1.setTopic("General");
        quiz1.setTimeLimit(30);
        quizDAO.addQuiz(quiz1);

        Quiz quiz2 = new RealQuiz("Gio", date, "quiz2", "trueFalse", "Gio's Quiz", "format");
        quiz2.setNumQuestions(15);
        quiz2.setTopic("Math");
        quiz2.setTimeLimit(45);
        quizDAO.addQuiz(quiz2);

        List<Quiz> multipleChoice = quizDAO.getAllByType("multipleChoice");
        assertEquals(1, multipleChoice.size());
        assertEquals("quiz1", multipleChoice.get(0).getID());

        List<Quiz> mathQuizzes = quizDAO.getAllbyTopic("Math");
        assertEquals(1, mathQuizzes.size());
        assertEquals("quiz2", mathQuizzes.get(0).getID());

        List<Quiz> mzareQuizzes = quizDAO.getAllbyAuthor("Mzare");
        assertEquals(1, mzareQuizzes.size());
        assertEquals("quiz1", mzareQuizzes.get(0).getID());
    }

    @Test
    public void testModifyQuiz() {
        Date date = new Date();
        Quiz quiz1 = new RealQuiz("Mzare", date, "quiz1", "multipleChoice", "Mzare's Quiz", "Form");
        quiz1.setNumQuestions(10);
        quiz1.setTopic("General");
        quiz1.setTimeLimit(30);
        quizDAO.addQuiz(quiz1);

        Quiz quiz2 = new RealQuiz("Gio", date, "quiz1", "multipleChoice", "Gio's Quiz", "NewForm");
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
        assertEquals("NewForm", modified.getPageFormat());
    }


    @Test
    public void testMultipleQuizzes() {
        Date date = new Date();
        for (int i = 1; i <= 5; i++) {
            Quiz quiz = new RealQuiz("Author" + i, date, "quiz" + i, "type" + i, "Quiz " + i, "format" + i);
            quizDAO.addQuiz(quiz);
        }

        assertEquals(5, quizDAO.getNumQuizes());
        List<Quiz> allQuizzes = quizDAO.getAll();
        assertEquals(5, allQuizzes.size());

        quizDAO.removeQuiz(allQuizzes.get(0));
        quizDAO.removeQuiz(allQuizzes.get(1));

        assertEquals(3, quizDAO.getNumQuizes());
    }

    @Test(expected = RuntimeException.class)
    public void testAddDuplicateQuiz() {
        Date date = new Date();
        Quiz quiz = new RealQuiz("Author", date, "quiz1", "type", "Quiz", "format");
        quizDAO.addQuiz(quiz);
        quizDAO.addQuiz(quiz);
    }


}