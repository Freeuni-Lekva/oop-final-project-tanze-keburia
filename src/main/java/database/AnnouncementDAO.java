package classes;

import java.sql.Connection;
import java.util.List;

public class AnnouncementDAO {
    private final Connection conn;
    public AnnouncementDAO(Connection conn) {
        this.conn = conn;
    }
    public void init() {

    }
    public void addAnnouncement(Announcement toAdd) {

    }
    public void removeAnnouncement(String announcementID) {

    }
    public void modifyAnnouncement(Announcement x) {

    }
    public List<Announcement> getAllAnnouncements() {
        return null;
    }
}
