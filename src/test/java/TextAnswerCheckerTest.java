import classes.quiz_utilities.*;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.RealQuestionDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextAnswerCheckerTest  {
    private static Connection conn;
    private static QuestionDAO questionDAO;
    private static TextAnswerChecker answerChecker;
    @BeforeAll

    public static void init() throws SQLException {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        conn = dbc.getConnection();
        questionDAO = new RealQuestionDAO(conn);
        System.out.println(questionDAO);
        questionDAO.initialize();
        answerChecker = new TextAnswerChecker(questionDAO);
        assert conn != null;
    }
    @BeforeEach
    public void clearTable() throws SQLException {
        Statement stmt = conn.createStatement();
        questionDAO.initialize();
        stmt.close();
    }
    @Test
    public void test() {
        Question q = new RealQuestion("abc", "a", "1", "1", "1");
        SingleAnswer answer = new SingleAnswer("1", "a");
        SingleAnswer answer2 = new SingleAnswer("1", "b");
        questionDAO.addQuestion(q);

        assertEquals(1.0, answerChecker.getPoints("1", answer), 1e-6);
        assertEquals(0.0, answerChecker.getPoints("1", answer2), 1e-6);
    }
    @Test
    public void testBad() {
        Question q = new RealQuestion("abc", "a", "1", "1", "1");
        MultipleAnswer answer = new MultipleAnswer("2", new ArrayList<>(Arrays.asList("aa", "bbb")));
        questionDAO.addQuestion(q);
        Exception exception = assertThrows(RuntimeException.class, () -> answerChecker.getPoints("1", answer));
        assertEquals("Exactly 1 answer is allowed", exception.getMessage());
    }


}
