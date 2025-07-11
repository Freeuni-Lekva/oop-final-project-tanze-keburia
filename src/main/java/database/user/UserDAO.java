package database.user;

import classes.security.Hasher;
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
        String sql = "DROP TABLE IF EXISTS users";
        Statement stmt = conn.createStatement();
       // stmt.execute(sql);
        sql =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "userName VARCHAR(50) PRIMARY KEY, " +
                        "passwordHash VARCHAR(60) NOT NULL, " +
                        "is_admin BOOLEAN DEFAULT FALSE)"; // Added is_admin column
        stmt.execute(sql);
        stmt.close();
    }

    public void addUser(User u) {
        String sql =  "INSERT INTO users (userName, passwordHash) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUserName());
            stmt.setString(2, u.getPassword());
            stmt.executeUpdate();
        } catch (SQLException e) {e.printStackTrace();}
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
        } catch (SQLException e) {e.printStackTrace();}
        catch (NoSuchAlgorithmException e) {throw new RuntimeException(e);}
        return false;
    }

    public boolean userExists(String username) {
        String sql = "SELECT 1 FROM users WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {e.printStackTrace();}
        return false;
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

    public void setAdminStatus(String username, boolean isAdmin) throws SQLException {
        String sql = "UPDATE users SET is_admin = ? WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, isAdmin);
            stmt.setString(2, username);
            stmt.executeUpdate();
        }
    }


    public boolean isAdmin(String username) throws SQLException {
        String sql = "SELECT is_admin FROM users WHERE userName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getBoolean("is_admin");
        }
    }
}