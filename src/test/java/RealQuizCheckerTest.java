import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.MultipleAnswer;
import classes.quiz_utilities.answer.SingleAnswer;
import classes.quiz_utilities.checkers.*;
import classes.quiz_utilities.options.Option;
import classes.quiz_utilities.questions.RealQuestion;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.OptionsDAO;
import database.quiz_utilities.QuestionDAO;
import database.quiz_utilities.RealQuestionDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RealQuizCheckerTest {
    private static QuestionDAO questions;
    private static OptionsDAO options;
    @BeforeAll
    public static void setUp() throws SQLException {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        Connection conn = dbc.getConnection();
        questions = new RealQuestionDAO(conn);
        questions.initialize();
        options = new  OptionsDAO(conn);
        options.initialize();
        System.out.println(questions);
    }
    @Test
    void testSingle() throws SQLException {
        AnswerChecker answerChecker = new TextAnswerChecker(questions);
        QuizChecker checker = new RealQuizChecker(answerChecker);
        questions.addQuestion(new RealQuestion("a", "b", "1", "1", "2.0"));
        questions.addQuestion(new RealQuestion("c", "d", "1", "2", "1.0"));
        Map<String, GeneralAnswer> mp = new HashMap<>();
        mp.put("1", new SingleAnswer("1", "b"));
        mp.put("2", new SingleAnswer("2", "c"));
        Map<String, Double> res = checker.checkedAnswers(mp);
        assertEquals(2.0, res.get("1"), 1e-6);
        assertEquals(0.0, res.get("2"), 1e-6);
    }
    @Test
    void testMultiple() throws SQLException {
        AnswerChecker answerChecker = new MultipleChoiceChecker(options);
        QuizChecker checker = new RealQuizChecker(answerChecker);
        options.addOption(new Option("1", "1", "a", 0.5));
        options.addOption(new Option("2", "2", "b", 1.0));
        options.addOption(new Option("3", "3", "c", 1.0));
        options.addOption(new Option("1", "4", "d", 1.0));
        Map<String, GeneralAnswer> mp = new HashMap<>();
        mp.put("1", new MultipleAnswer("1", Arrays.asList("a")));
        mp.put("2", new MultipleAnswer("2", Arrays.asList("b")));
        mp.put("3", new MultipleAnswer("3", Collections.EMPTY_LIST));
        Map<String, Double> res = checker.checkedAnswers(mp);
        assertEquals(0.5, res.get("1"), 1e-6);
        assertEquals(1.0, res.get("2"), 1e-6);
        assertEquals(0, res.get("3"), 1e-6);
    }
}
