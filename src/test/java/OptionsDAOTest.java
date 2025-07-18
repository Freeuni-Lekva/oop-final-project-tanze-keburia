import classes.quiz_utilities.options.Option;
import database.quiz_utilities.OptionsDAO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OptionsDAOTest {
    private static OptionsDAO options;
    @BeforeAll
    public static void setUpClass() throws SQLException {
       Connection conn =  DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        options = new OptionsDAO(conn);
    }
    @BeforeEach
    public void init() throws SQLException {
        options.initialize();
    }
    @Test
    public void testAdd() throws SQLException {
        Option option = new Option("A", "1", "a", 2.0);
        options.addOption(option);
        assertEquals(1, options.getNumberOfOptions());
    }
    @Test
    public void testRemove() throws SQLException {
        Option option = new Option("A", "1", "a", 2.0);
        options.addOption(option);
        options.removeOption(option.getOptionID());
        Option option2 = new Option("B", "2", "b", 2.0);
        assertEquals(0, options.getNumberOfOptions());
        options.removeOption(option.getOptionID());
        assertEquals(0, options.getNumberOfOptions());
    }
    @Test
    public void testUpdate() throws SQLException {
        Option option = new Option("A", "1", "a", 2.0);
        options.addOption(option);
        option.setAnswer("b");
        options.updateOption(option);
        Option updated = options.getOptionByID(option.getOptionID());
        assertEquals(option.getAnswer(), updated.getAnswer());
    }
    @Test
    public void testGetOptionByID() throws SQLException {
        Option option = new Option("A", "1", "a", 2.0);
        options.addOption(option);
        Option x = options.getOptionByID(option.getOptionID());
        assertEquals(option.getOptionID(), x.getOptionID());
        assertEquals(option.getAnswer(), x.getAnswer());
        assertEquals(option.getQuestionID(), x.getQuestionID());
        assertEquals(option.getPoints(), x.getPoints(), 1e-6);
      //  option = new Option("1", "2", "a", 2.0);
        x = options.getOptionByID("3");
        assertNull(x);
    }
    @Test
    public void testGetAllOptionsByQuestion() throws SQLException {
        Option option = new Option("A", "1", "a", 2.0);
        Option option1 = new Option("A", "2", "b", 0.0);
        Option option2 = new Option("B","3", "a", 1);
        options.addOption(option);
        options.addOption(option1);
        List<Option> res = options.getOptionsByQuestion("A");
        assertEquals(2, res.size());
        assertEquals("A", res.get(0).getQuestionID());
        assertEquals("A", res.get(1).getQuestionID());
        assertTrue(res.get(0).getAnswer().equals("b") || res.get(0).getAnswer().equals("a"));
        assertTrue(res.get(1).getAnswer().equals("b") || res.get(1).getAnswer().equals("a"));
        options.removeOption(option.getOptionID());
        res = options.getOptionsByQuestion("A");
        assertEquals(1, res.size());
    }

}
