<<<<<<<< HEAD:src/main/java/classes/User/UserDAO.java
package classes.User;

import classes.Hasher;
========
package database;

import classes.Hasher;
import classes.User;
>>>>>>>> df1a6eb3c0b7ed44c13ce04e76d91a2b760b57ed:src/main/java/database/UserDAO.java

import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) throws SQLException {
        this.conn = conn;
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