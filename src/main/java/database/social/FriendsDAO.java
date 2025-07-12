package database.social;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendsDAO {
    private Connection conn;

    public FriendsDAO(Connection conn) {
        this.conn = conn;
        int pp = 1;
    }
    public void initialize() {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS friends");
            stmt.execute("CREATE TABLE IF NOT EXISTS friends (" +
                    "user_a VARCHAR(255), " +
                    "user_b VARCHAR(255), " +
                    "PRIMARY KEY (user_a, user_b), " +
                    "CHECK (user_a < user_b))");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }
    public void addFriends(String username, String friendUsername) {
        if(username == null || friendUsername == null || username.equals(friendUsername)) {
            throw new IllegalArgumentException("Invalid usernames");
        }
        String user1 = username.compareTo(friendUsername) < 0 ? username : friendUsername;
        String user2 = username.compareTo(friendUsername) < 0 ? friendUsername : username;

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO friends (user_a, user_b) VALUES (?, ?)")) {

            ps.setString(1, user1);
            ps.setString(2, user2);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add friends", e);
        }
    }

    public void removeFriends(String a, String b) {
        if (a == null || b == null || a.equals(b)) {
            throw new IllegalArgumentException("Invalid usernames");
        }

        String user1 = a.compareTo(b) < 0 ? a : b;
        String user2 = a.compareTo(b) < 0 ? b : a;

        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM friends WHERE user_a = ? AND user_b = ?")) {

            ps.setString(1, user1);
            ps.setString(2, user2);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove friends", e);
        }
    }

    public List<String> getFriends(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        List<String> friends = new ArrayList<>();
        try {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT user_b FROM friends WHERE user_a = ?")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        friends.add(rs.getString("user_b"));
                    }
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT user_a FROM friends WHERE user_b = ?")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        friends.add(rs.getString("user_a"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get friends", e);
        }
        return friends;
    }


}
