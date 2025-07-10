package database.achievement;


import classes.achievement.Achievement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AchievementDAO {
    private final Connection conn;

    public AchievementDAO(Connection conn) {
        this.conn = conn;
    }

    public void initialize() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS achievements");
            stmt.execute("CREATE TABLE achievements (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(255) NOT NULL, " +
                    "type VARCHAR(255) NOT NULL, " +
                    "awarded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize achievements table", e);
        }
    }

    public void awardAchievement(String username, String type) {
        if (hasAchievement(username, type)) return;

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO achievements (username, type) VALUES (?, ?)")) {
            ps.setString(1, username);
            ps.setString(2, type);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to award achievement", e);
        }
    }

    public boolean hasAchievement(String username, String type) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM achievements WHERE username = ? AND type = ?")) {
            ps.setString(1, username);
            ps.setString(2, type);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check achievement", e);
        }
    }

    public List<Achievement> getUserAchievements(String username) {
        List<Achievement> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM achievements WHERE username = ? ORDER BY awarded_at")) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Achievement(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("type"),
                            rs.getTimestamp("awarded_at")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get achievements", e);
        }
        return list;
    }
}