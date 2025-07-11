import classes.achievement.Achievement;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

public class AchievementTest {

    @Test
    public void testAchievementConstructorAndGetters() {
        int id = 1;
        String username = "testUser";
        String type = "FirstLogin";
        Timestamp awardedAt = new Timestamp(System.currentTimeMillis());

        Achievement achievement = new Achievement(id, username, type, awardedAt);

        assertEquals(id, achievement.getId());
        assertEquals(username, achievement.getUsername());
        assertEquals(type, achievement.getType());
        assertEquals(awardedAt, achievement.getAwardedAt());
    }
}
