package database;

import classes.Quiz;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class QuizDAOTest {
    private static Connection conn;
    private static QuizDAO quizDao;

    @BeforeClass
    public static void setUpClass() throws Exception {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        conn = dbc.getConnection();

        quizDao = new QuizDAO(conn);

        // Setup test table
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS quizzes");
            stmt.execute("CREATE TABLE quizzes (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "quiz_name VARCHAR(255) NOT NULL, " +
                    "quiz_index INT NOT NULL, " +
                    "creation_date DATETIME NOT NULL, " +
                    "author VARCHAR(255) NOT NULL, " +
                    "time_limit INT NOT NULL, " +
                    "question_quantity INT NOT NULL, " +
                    "topic VARCHAR(255) NOT NULL, " +
                    "UNIQUE(quiz_index)" +
                    ")");
        }
    }

    @Test
    public void testQuizLifecycle() throws Exception {
        // Create test quiz
        Date date = new Date(System.currentTimeMillis());
        Quiz quiz = new Quiz("Everest", 0, date, "Mzare", 1000, 20, "math");

        quizDao.createQuiz(quiz);
        List<Quiz> quizzes = quizDao.getQuizzes();
        assertEquals(1, quizzes.size());

        List<Quiz> mathQuizzes = quizDao.topicFilter("math");
        assertEquals(1, mathQuizzes.size());
        assertEquals(0, quizDao.topicFilter("mat").size());

        assertTrue(quizDao.quizDelete(0));
        assertEquals(0, quizDao.getQuizzes().size());
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}