package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FriendRequestDAO {
    private final Connection conn;
    private final FriendsDAO friendsDAO;

    public FriendRequestDAO(Connection conn, FriendsDAO friendsDAO) {
        this.conn = conn;
        this.friendsDAO = friendsDAO;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS requests (" +
                    "sender VARCHAR(255) NOT NULL, " +
                    "receiver VARCHAR(255) NOT NULL, " +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    "PRIMARY KEY (sender, receiver), " +
                    "CHECK (sender <> receiver))");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize requests database", e);
        }
    }

    public void createRequest(String sender, String receiver) {
        if (sender == null || receiver == null || sender.equals(receiver)) {
            throw new IllegalArgumentException("Invalid sender or receiver");
        }

        if (areAlreadyFriends(sender, receiver)) {
            throw new IllegalStateException("Users are already friends");
        }

        if (requestExists(sender, receiver)) {
            throw new IllegalStateException("Friend request already exists");
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO requests (sender, receiver) VALUES (?, ?)")) {
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create friend request", e);
        }
    }

    private boolean areAlreadyFriends(String user1, String user2) {
        List<String> user1Friends = friendsDAO.getFriends(user1);
        return user1Friends.contains(user2);
    }

    private boolean requestExists(String sender, String receiver) {
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM requests WHERE sender = ? AND receiver = ?")) {
            ps.setString(1, sender);
            ps.setString(2, receiver);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check request existence", e);
        }
    }

    public void deleteRequest(String sender, String receiver) {
        if (sender == null || receiver == null || sender.equals(receiver)) {
            throw new IllegalArgumentException("Invalid sender or receiver");
        }

        try (PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM requests WHERE sender = ? AND receiver = ?")) {
            ps.setString(1, sender);
            ps.setString(2, receiver);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete friend request", e);
        }
    }

    public List<String> getRequestList(String username) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }

        List<String> requests = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT sender FROM requests WHERE receiver = ? " +
                        "AND sender NOT IN (SELECT user_b FROM friends WHERE user_a = ? UNION " +
                        "SELECT user_a FROM friends WHERE user_b = ?) " +
                        "ORDER BY created_at DESC")) {
            ps.setString(1, username);
            ps.setString(2, username);
            ps.setString(3, username);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    requests.add(rs.getString("sender"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get request list", e);
        }
        return requests;
    }
}