package database.admin;

import classes.admin.Announcement;

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
        try(PreparedStatement stmt = conn.prepareStatement("DROP TABLE IF EXISTS announcements")) {
            stmt.execute();
        }

        try(PreparedStatement stmt = conn.prepareStatement(
                "CREATE TABLE announcements(" +
                        "ID VARCHAR(255) NOT NULL," +
                        "author VARCHAR(255) NOT NULL," +
                        "body TEXT," +
                        "created_date DATETIME NOT NULL," +
                        "PRIMARY KEY (ID))"
        )) {
            stmt.execute();
        }
    }

    public void addAnnouncement(Announcement toAdd) throws SQLException {
        try(PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO announcements(author, body, ID, created_date) VALUES(?, ?, ?, ?)"
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
                "SELECT * FROM announcements ORDER BY created_date DESC"
        )){
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()) {
                    result.add(new Announcement(
                            rs.getString("ID"),
                            rs.getString("author"),
                            rs.getString("body"),
                            rs.getTimestamp("created_date")
                    ));
                }
            }
        }
        return result;
    }

    public Announcement getLatestAnnouncement() throws SQLException {
        String sql = "SELECT * FROM announcements ORDER BY created_date DESC LIMIT 1";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return new Announcement(
                        rs.getString("ID"),
                        rs.getString("author"),
                        rs.getString("body"),
                        rs.getTimestamp("created_date")
                );
            }
        }
        return null;
    }
}