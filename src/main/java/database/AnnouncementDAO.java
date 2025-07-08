package database;

import classes.Announcement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDAO {
    private final Connection conn;
    public AnnouncementDAO(Connection conn) {
        this.conn = conn;
    }
    public void initialize() throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "DROP TABLE IF EXISTS announcements;"+
                "CREATE TABLE announcements(" +
                        "author VARCHAR(255) NOT NULL," +
                        "body VARCHAR(280000)," +
                        "ID VARCHAR(255) NOT NULL," +
                        "DATE DATETIME NOT NULL," +
                "PRIMARY KEY (ID))")
        ) {
            stmt.execute();
        }
    }
    public void addAnnouncement(Announcement toAdd) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO announcements(author, body, ID, DATE) VALUES(?, ?, ?, ?);"
        )){
            stmt.setString(1, toAdd.getAuthor());
            stmt.setString(2, toAdd.getBody());
            stmt.setString(3, toAdd.getId());
            stmt.setTimestamp(4, toAdd.getPublishDate());
            stmt.executeUpdate();
        }
    }
    public void removeAnnouncement(String announcementID) throws SQLException{
        try(PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM announcements WHERE ID=?"
        )){
            stmt.setString(1, announcementID);
            stmt.executeUpdate();
        }
    }
    public void modifyAnnouncement(Announcement x) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "UPDATE announcements SET body=? WHERE ID=?"
        )){
            stmt.setString(1, x.getBody());
            stmt.setString(2, x.getId());
            stmt.executeUpdate();
        }
    }
    public List<Announcement> getAllAnnouncements() throws SQLException {
        List<Announcement> result = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM announcements ORDER BY DATE DESC"
        )){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()) {
                    result.add(new Announcement(
                            rs.getString("ID"),
                            rs.getString("AUTHOR"),
                            rs.getString("BODY"),
                            rs.getTimestamp("DATE")
                    ));
                }
            }
        }
        return result;
    }
}
