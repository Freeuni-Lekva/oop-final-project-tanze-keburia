package database;

import classes.Hasher;
import classes.User;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public void initialize() throws SQLException {
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                    "userName VARCHAR(50) PRIMARY KEY, " +
                    "passwordHash VARCHAR(60) NOT NULL)");
        }
    }

    public void addUser(User u) {
        String sql = "INSERT INTO users (userName, passwordHash) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUserName());
            stmt.setString(2, u.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {throw new RuntimeException("Failed to add user: " + e.getMessage(), e);}
    }

    public boolean checkPassword(String username, String password) {
        String sql = "SELECT passwordHash FROM users WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("passwordHash");
                return storedHash.equals(Hasher.hashPassword(password));
            }
            return false;
        } catch (SQLException | NoSuchAlgorithmException e) {throw new RuntimeException("Failed to check password: " + e.getMessage(), e);}
    }

    public boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {throw new RuntimeException("Failed to check if user exists: " + e.getMessage(), e);}
    }

    public boolean removeUser(String username) {
        String sql = "DELETE FROM users WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {throw new RuntimeException("Failed to remove user: " + e.getMessage(), e);}
    }

    public List<String> getAllUsers() {
        List<String> users = new ArrayList<>();
        String sql = "SELECT userName FROM users ORDER BY userName";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                users.add(rs.getString("userName"));
            }
            return users;
        } catch (SQLException e) {throw new RuntimeException("Failed to get all users: " + e.getMessage(), e);}
    }

    public int getUserCount() {
        String sql = "SELECT COUNT(*) FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {throw new RuntimeException("Failed to get user count: " + e.getMessage(), e);}
    }
}