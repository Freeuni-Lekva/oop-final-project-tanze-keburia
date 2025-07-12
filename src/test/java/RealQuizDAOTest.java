
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

    @Test
    public void testGetRecentQuizzes() {
        long now = System.currentTimeMillis();
        Quiz q1 = new RealQuiz("Author1", new Date(now - 100000), "quiz1", "typeA", "Quiz 1", "page");
        Quiz q2 = new RealQuiz("Author2", new Date(now - 50000), "quiz2", "typeA", "Quiz 2", "page");
        Quiz q3 = new RealQuiz("Author3", new Date(now), "quiz3", "typeA", "Quiz 3", "page");

        q1.setNumQuestions(5);
        q2.setNumQuestions(5);
        q3.setNumQuestions(5);

        quizDAO.addQuiz(q1);
        quizDAO.addQuiz(q2);
        quizDAO.addQuiz(q3);

        try {
            List<Quiz> recents = quizDAO.getRecentQuizzes(2);
            assertEquals(2, recents.size());
            assertEquals("quiz3", recents.get(0).getID());
            assertEquals("quiz2", recents.get(1).getID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        quizDAO.removeQuiz(q1);
        quizDAO.removeQuiz(q2);
        quizDAO.removeQuiz(q3);
    }

    @Test
    public void testGetPopularQuizzes() throws SQLException {
        Date now = new Date();

        Quiz quiz1 = new RealQuiz("Author1", now, "quiz1", "typeA", "Quiz One", "format");
        quiz1.setNumQuestions(5);
        quiz1.setTopic("Topic1");
        quiz1.setTimeLimit(10);
        quiz1.setPageFormat("one-pager");
        quiz1.setPlayCount(10);

        Quiz quiz2 = new RealQuiz("Author2", now, "quiz2", "typeA", "Quiz Two", "format");
        quiz2.setNumQuestions(5);
        quiz2.setTopic("Topic2");
        quiz2.setTimeLimit(15);
        quiz2.setPageFormat("one-pager");
        quiz2.setPlayCount(50);

        Quiz quiz3 = new RealQuiz("Author3", now, "quiz3", "typeA", "Quiz Three", "format");
        quiz3.setNumQuestions(5);
        quiz3.setTopic("Topic3");
        quiz3.setTimeLimit(20);
        quiz3.setPageFormat("one-pager");
        quiz3.setPlayCount(30);

        quizDAO.addQuiz(quiz1);
        quizDAO.addQuiz(quiz2);
        quizDAO.addQuiz(quiz3);

        List<Quiz> popular = quizDAO.getPopularQuizzes(2);

        assertEquals(2, popular.size());
        assertEquals("quiz2", popular.get(0).getID());
        assertEquals("quiz3", popular.get(1).getID());

        quizDAO.removeQuiz(quiz1);
        quizDAO.removeQuiz(quiz2);
        quizDAO.removeQuiz(quiz3);
    }


    @Test
    public void testIncrementPlayCount() throws SQLException {
        Date now = new Date();

        Quiz quiz = new RealQuiz("Author", now, "quizTest", "type", "Increment Test Quiz", "format");
        quiz.setNumQuestions(5);
        quiz.setTopic("Testing");
        quiz.setTimeLimit(10);
        quiz.setPageFormat("one-pager");
        quiz.setPlayCount(0);

        quizDAO.addQuiz(quiz);

        quizDAO.incrementPlayCount("quizTest");
        quizDAO.incrementPlayCount("quizTest");
        quizDAO.incrementPlayCount("quizTest");

        Quiz updatedQuiz = quizDAO.getQuiz("quizTest");

        assertNotNull(updatedQuiz);
        assertEquals(3, updatedQuiz.getPlayCount());

        quizDAO.removeQuiz(updatedQuiz);
    }


    @Test
    public void testGetRecentlyCreatedQuizzesByUser() throws SQLException {
        long now = System.currentTimeMillis();

        Quiz q1 = new RealQuiz("testUser", new Date(now - 100000), "quizA", "typeX", "Quiz A", "one-pager");
        Quiz q2 = new RealQuiz("testUser", new Date(now - 50000), "quizB", "typeX", "Quiz B", "one-pager");
        Quiz q3 = new RealQuiz("testUser", new Date(now), "quizC", "typeX", "Quiz C", "one-pager");
        Quiz q4 = new RealQuiz("otherUser", new Date(now), "quizD", "typeX", "Quiz D", "one-pager");

        q1.setNumQuestions(5);
        q2.setNumQuestions(5);
        q3.setNumQuestions(5);
        q4.setNumQuestions(5);

        quizDAO.addQuiz(q1);
        quizDAO.addQuiz(q2);
        quizDAO.addQuiz(q3);
        quizDAO.addQuiz(q4);

        List<Quiz> recentByTestUser = quizDAO.getRecentlyCreatedQuizzesByUser("testUser", 2);

        assertEquals(2, recentByTestUser.size());
        assertEquals("quizC", recentByTestUser.get(0).getID());
        assertEquals("quizB", recentByTestUser.get(1).getID());

        quizDAO.removeQuiz(q1);
        quizDAO.removeQuiz(q2);
        quizDAO.removeQuiz(q3);
        quizDAO.removeQuiz(q4);
    }



    @Test
    public void testGetQuizNameById() throws SQLException {
        long now = System.currentTimeMillis();
        quizDAO.addQuiz(new RealQuiz("testUser", new Date(now - 100000), "quizA", "typeX", "Quiz A", "one-pager"));
        String quizName = quizDAO.getQuizNameById("quizA");
        assertEquals("Quiz A", quizName);
        assertEquals(1, quizDAO.getCreatedQuizCount("testUser"));
    }

}