import classes.quiz_utilities.answer.GeneralAnswer;
import classes.quiz_utilities.answer.MultipleAnswer;
import classes.quiz_utilities.checkers.MultipleChoiceChecker;
import classes.quiz_utilities.options.Option;
import database.quiz_utilities.OptionsDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleChoiceCheckerTest {
    private static OptionsDAO options;
    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {

        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        options = new OptionsDAO(conn);

    }
    @BeforeEach
    public void init() throws SQLException {
        options.initialize();
        options.addOption(new Option("A", "1", "a", 0.5));
        options.addOption(new Option("A", "2", "b", 0.5));
        options.addOption(new Option("A", "3", "c", 0.5));
        options.addOption(new Option("A", "4", "d", 0.5));
    }
    @Test
    public void test() throws SQLException {
        GeneralAnswer answer = new MultipleAnswer("A", Arrays.asList(" a", "b", "c"));
        MultipleChoiceChecker checker = new MultipleChoiceChecker(options);
        double points = checker.getPoints("A", answer);
        assertEquals(1.5, points, 1e-6);
    }
    @Test
    public void testEmpty() throws SQLException {
        GeneralAnswer answer= new MultipleAnswer("A", Collections.EMPTY_LIST);
        MultipleChoiceChecker checker = new MultipleChoiceChecker(options);
        double points = checker.getPoints("A", answer);
        assertEquals(0.0, points, 1e-6);
        assertEquals(0.0, checker.getPoints("A", null));
    }
    @Test
    public void testInvalid() throws SQLException {
        GeneralAnswer answer= new MultipleAnswer("B", Arrays.asList("a", "b", "c"));
        MultipleChoiceChecker checker = new MultipleChoiceChecker(options);
        double points = checker.getPoints("A", answer);
        assertEquals(0.0, points, 1e-6);
    }
}
