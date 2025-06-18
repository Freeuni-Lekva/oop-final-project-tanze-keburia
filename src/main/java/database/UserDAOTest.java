<<<<<<<< HEAD:src/main/java/classes/UserDAOTest.java
import classes.DataBases.DatabaseConnector;
import classes.User.User;
import classes.User.UserDAO;
========
<<<<<<<< HEAD:src/main/java/database/UserDAOTest.java
package database;

import classes.User;
========
package tests;

import classes.DatabaseConnector;
import classes.User;
import classes.UserDAO;
>>>>>>>> 6f594cb85381d40863ae333ff9a68bf1cb77fe35:src/main/java/tests/UserDAOTest.java
>>>>>>>> df1a6eb3c0b7ed44c13ce04e76d91a2b760b57ed:src/main/java/database/UserDAOTest.java
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class UserDAOTest {
    private static Connection conn;
    private UserDAO userDao;
    private User u;

    @BeforeClass
    public static void setUpClass() throws Exception {
<<<<<<<< HEAD:src/main/java/database/UserDAOTest.java
        String url = "jdbc:mysql://localhost:3306/metro";
        String username = "icosahedron";
        String password = "Loko_kina1";
========
        String url = "jdbc:mysql://localhost:3306/mysql";
        String username = "root";
        String password = "Akkdzidzi100!";
>>>>>>>> 6f594cb85381d40863ae333ff9a68bf1cb77fe35:src/main/java/tests/UserDAOTest.java
        DatabaseConnector dbc = DatabaseConnector.getInstance(url, username, password);
        conn = dbc.getConnection();
        assert(conn != null);
        // Create users table
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS users");
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "userName VARCHAR(50) PRIMARY KEY, " +
                            "passwordHash VARCHAR(60) NOT NULL)");
        }
    }

    @Before
    public void setUp() throws SQLException {
        userDao = new UserDAO(conn);
    }

    @Test
    public void testAddingUser() throws NoSuchAlgorithmException {
        u = new User("mzare", "1234");
        assertFalse(userDao.userExists(u.getUserName()));
        userDao.addUser(u);
        assertTrue(userDao.userExists(u.getUserName()));
    }
    @Test
    public void testCheckPassword() throws NoSuchAlgorithmException {
        User user = new User("testuser", "password123");
        userDao.addUser(user);
        assertTrue(userDao.checkPassword("testuser", "password123"));
        assertFalse(userDao.checkPassword("testuser", "wrongpassword"));
    }
}