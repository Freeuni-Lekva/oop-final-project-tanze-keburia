import classes.admin.Announcement;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class AnnouncementTest {
    @Test
    public void AnnouncementTest() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Announcement x = new Announcement("1", "Bingo", "Bobo", date);
        assertEquals("Bingo", x.getAuthor());
        assertEquals("Bobo", x.getBody());
        assertEquals(date, x.getPublishDate());
        assertEquals("1", x.getId());
        x.setBody("Boom");
        assertEquals("Boom", x.getBody());
    }
    @Test
    public void testEquals() {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Announcement x = new Announcement("1", "Bingo", "Bobo", date);
        Announcement y = new Announcement("1", "Bingo", "Bobo", null);
        assertEquals(x, y);
        y = new Announcement("2", "Bingo", "Bobo", date);
        assertNotEquals(x, y);
        assertEquals(x, x);
        assertNotEquals(x, date);
        assertNotEquals(x, null);
    }
}
