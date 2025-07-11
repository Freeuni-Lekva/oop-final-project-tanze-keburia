import classes.achievement.Achievement;
import database.achievement.AchievementDAO;
import database.database_connection.DatabaseConnector;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class AchievementDAOTest {
    private static Connection conn;
    private static AchievementDAO achievementDAO;

    @BeforeClass
    public static void setUpClass() throws Exception {
        conn = DatabaseConnector.getInstance().getConnection();
        achievementDAO = new AchievementDAO(conn);
        achievementDAO.initialize();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Before
    public void clearTable() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM achievements");
        }
    }

    @Test
    public void testAwardAndRetrieveAchievements() {
        achievementDAO.awardAchievement("guga", "quiz_master");
        achievementDAO.awardAchievement("guga", "early_bird");

        List<Achievement> achievements = achievementDAO.getUserAchievements("guga");

        assertEquals(2, achievements.size());
        assertEquals("quiz_master", achievements.get(0).getType());
        assertEquals("early_bird", achievements.get(1).getType());
    }

    @Test
    public void testPreventDuplicateAward() {
        achievementDAO.awardAchievement("ako", "dedicated");

        // Try to award the same achievement again
        achievementDAO.awardAchievement("ako", "dedicated");

        List<Achievement> achievements = achievementDAO.getUserAchievements("ako");
        assertEquals(1, achievements.size());
        assertEquals("dedicated", achievements.get(0).getType());
    }

    @Test
    public void testHasAchievement() {
        achievementDAO.awardAchievement("mari", "finisher");
        assertTrue(achievementDAO.hasAchievement("mari", "finisher"));
        assertFalse(achievementDAO.hasAchievement("mari", "starter"));
    }

    @Test
    public void testGetAchievementsEmpty() {
        assertTrue(achievementDAO.getUserAchievements("nonexistent_user").isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void testAwardAchievementSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        AchievementDAO badDAO = new AchievementDAO(badConn);
        badDAO.awardAchievement("user", "broken");
    }

    @Test(expected = RuntimeException.class)
    public void testHasAchievementSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        AchievementDAO badDAO = new AchievementDAO(badConn);
        badDAO.hasAchievement("user", "broken");
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserAchievementsSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        AchievementDAO badDAO = new AchievementDAO(badConn);
        badDAO.getUserAchievements("user");
    }

    @Test(expected = RuntimeException.class)
    public void testInitializeSQLException() throws Exception {
        Connection badConn = DatabaseConnector.getInstance().getConnection();
        badConn.close();
        AchievementDAO badDAO = new AchievementDAO(badConn);
        badDAO.initialize();
    }

    @Test
    public void testAchievementObjectFields() {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        Achievement achievement = new Achievement(1, "tester", "perfect", now);

        assertEquals(1, achievement.getId());
        assertEquals("tester", achievement.getUsername());
        assertEquals("perfect", achievement.getType());
        assertEquals(now, achievement.getAwardedAt());
    }
}
