import classes.admin.Announcement;
import database.admin.AnnouncementDAO;
import database.database_connection.DatabaseConnector;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class AnnouncementDAOTest {
    private static Connection conn;
    private AnnouncementDAO announcementDAO;
    private Timestamp now;

    @BeforeClass
    public static void setUpClass() throws Exception {
        conn = DatabaseConnector.getInstance().getConnection();
    }

    @Before
    public void setUp() throws Exception {
        announcementDAO = new AnnouncementDAO(conn);
        announcementDAO.initialize();
        now = new Timestamp(System.currentTimeMillis());
    }

    @After
    public void tearDown() throws Exception {
        announcementDAO.initialize(); // Cleans up the table
    }

    @Test
    public void testInitialize() throws SQLException {
        // The initialize() method is called in @Before, so we can verify it worked
        announcementDAO.addAnnouncement(new Announcement("test1", "admin", "Test content", now));
        List<Announcement> announcements = announcementDAO.getAllAnnouncements();
        assertEquals(1, announcements.size());
    }

    @Test
    public void testAddMultipleAnnouncements() throws SQLException {
        announcementDAO.addAnnouncement(new Announcement("ann1", "admin", "First", now));
        announcementDAO.addAnnouncement(new Announcement("ann2", "admin", "Second", new Timestamp(now.getTime() + 1000)));

        List<Announcement> announcements = announcementDAO.getAllAnnouncements();
        assertEquals(2, announcements.size());
        // Should be ordered by date descending
        assertEquals("ann2", announcements.get(0).getId());
        assertEquals("ann1", announcements.get(1).getId());
    }

    @Test
    public void testRemoveAnnouncement() throws SQLException {
        announcementDAO.addAnnouncement(new Announcement("ann1", "admin", "First", now));
        announcementDAO.addAnnouncement(new Announcement("ann2", "admin", "Second", now));

        announcementDAO.removeAnnouncement("ann1");

        List<Announcement> announcements = announcementDAO.getAllAnnouncements();
        assertEquals(1, announcements.size());
        assertEquals("ann2", announcements.get(0).getId());
    }

    @Test
    public void testRemoveNonExistentAnnouncement() throws SQLException {
        announcementDAO.addAnnouncement(new Announcement("ann1", "admin", "First", now));

        // Should not throw exception
        announcementDAO.removeAnnouncement("nonexistent");

        List<Announcement> announcements = announcementDAO.getAllAnnouncements();
        assertEquals(1, announcements.size());
    }

    @Test
    public void testModifyAnnouncement() throws SQLException {
        Announcement announcement = new Announcement("ann1", "admin", "Original content", now);
        announcementDAO.addAnnouncement(announcement);

        Announcement modified = new Announcement("ann1", "admin", "Updated content", now);
        announcementDAO.modifyAnnouncement(modified);

        List<Announcement> announcements = announcementDAO.getAllAnnouncements();
        assertEquals(1, announcements.size());
        assertEquals("Updated content", announcements.get(0).getBody());
    }

    @Test
    public void testModifyNonExistentAnnouncement() throws SQLException {
        // Should not throw exception
        announcementDAO.modifyAnnouncement(new Announcement("nonexistent", "admin", "Content", now));

        List<Announcement> announcements = announcementDAO.getAllAnnouncements();
        assertTrue(announcements.isEmpty());
    }

    @Test
    public void testGetAllAnnouncementsEmpty() throws SQLException {
        List<Announcement> announcements = announcementDAO.getAllAnnouncements();
        assertTrue(announcements.isEmpty());
    }

    @Test
    public void testGetLatestAnnouncement() throws SQLException {
        assertNull(announcementDAO.getLatestAnnouncement());

        Timestamp earlier = new Timestamp(now.getTime() - 10000);
        announcementDAO.addAnnouncement(new Announcement("ann1", "admin", "First", earlier));
        announcementDAO.addAnnouncement(new Announcement("ann2", "admin", "Second", now));

        Announcement latest = announcementDAO.getLatestAnnouncement();
        assertNotNull(latest);
        assertEquals("ann2", latest.getId());
        assertEquals("Second", latest.getBody());
    }

    @Test
    public void testGetLatestAnnouncementWhenEmpty() throws SQLException {
        assertNull(announcementDAO.getLatestAnnouncement());
    }

    @Test(expected = SQLException.class)
    public void testAddDuplicateAnnouncement() throws SQLException {
        Announcement announcement = new Announcement("ann1", "admin", "Content", now);
        announcementDAO.addAnnouncement(announcement);
        // Should throw exception
        announcementDAO.addAnnouncement(announcement);
    }

    @Test(expected = SQLException.class)
    public void testAddAnnouncementWithNullId() throws SQLException {
        Announcement announcement = new Announcement(null, "admin", "Content", now);
        announcementDAO.addAnnouncement(announcement);
    }

    @Test(expected = SQLException.class)
    public void testAddAnnouncementWithNullAuthor() throws SQLException {
        Announcement announcement = new Announcement("ann1", null, "Content", now);
        announcementDAO.addAnnouncement(announcement);
    }
}