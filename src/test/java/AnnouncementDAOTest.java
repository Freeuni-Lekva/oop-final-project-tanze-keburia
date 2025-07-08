import classes.Announcement;
import database.AnnouncementDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AnnouncementDAOTest {
    private static AnnouncementDAO announcements;
    @BeforeEach
    public void init() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
        announcements = new AnnouncementDAO(conn);
        announcements.initialize();
    }

    @Test
    public void testAddAnnouncement() throws SQLException {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Announcement x = new Announcement("1", "sys", "hello", date);
        announcements.addAnnouncement(x);
        Announcement y = new Announcement("2", "sys", "hell", new Timestamp(System.currentTimeMillis()));
        announcements.addAnnouncement(y);
        List<Announcement> ans =  announcements.getAllAnnouncements();

        assertEquals(2, ans.size());
        assertEquals(y, ans.get(0));
        assertEquals(x, ans.get(1));
    }
    @Test
    public void testRemoveAnnouncement() throws SQLException {
        Announcement x = new Announcement("1", "sys", "hello", new Timestamp(System.currentTimeMillis()));
        announcements.addAnnouncement(x);
        Announcement y = new Announcement("2", "sys", "hello1", new Timestamp(System.currentTimeMillis()));
        announcements.addAnnouncement(y);
        announcements.removeAnnouncement("1");
        List<Announcement> ans =  announcements.getAllAnnouncements();
        assertEquals(1, ans.size());
        assertEquals(y, ans.get(0));
    }
    @Test
    public void testUpdateAnnouncement() throws SQLException {
        Announcement x = new Announcement("1", "sys", "hello", new Timestamp(System.currentTimeMillis()));
        announcements.addAnnouncement(x);
        Announcement y = new Announcement("1", "sys", "hello1", new Timestamp(System.currentTimeMillis()));
        announcements.modifyAnnouncement(y);
        List<Announcement> ans = announcements.getAllAnnouncements();
        assertEquals(1, ans.size());
        assertEquals("hello1", ans.get(0).getBody());
    }

}
