import classes.quiz_utilities.GeneralAnswer;
import classes.quiz_utilities.MultipleAnswer;
import classes.quiz_utilities.MultipleChoiceChecker;
import classes.quiz_utilities.Option;
import database.database_connection.DatabaseConnector;
import database.quiz_utilities.OptionsDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MultipleChoiceCheckerTest {
    private static OptionsDAO options;
    @BeforeAll
    public static void setUp() throws SQLException {
        DatabaseConnector dbc = DatabaseConnector.getInstance();
        Connection conn = dbc.getConnection();
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
