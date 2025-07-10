package database.user;

import classes.security.Hasher;
import classes.User;

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) throws SQLException {
        this.conn = conn;

    }
    public void initialize() throws SQLException {
        String sql = "DROP TABLE IF EXISTS users";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        sql =
                "CREATE TABLE IF NOT EXISTS users (" +
                        "userName VARCHAR(50) PRIMARY KEY, " +
                        "passwordHash VARCHAR(60) NOT NULL)";
        stmt.execute(sql);
        stmt.close();
    }
    public void addUser(User u) {
        String sql =  "INSERT INTO users (userName, passwordHash) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getUserName());
            stmt.setString(2, u.getPassword());
            stmt.executeUpdate();
            stmt.close();
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
        } catch (SQLException e) {e.printStackTrace();} catch (NoSuchAlgorithmException e) {throw new RuntimeException(e);}
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
}