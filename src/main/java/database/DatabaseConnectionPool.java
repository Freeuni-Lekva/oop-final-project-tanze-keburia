package database;

public class DatabaseConnectionPool {
    public static final String url = "jdbc:mysql://localhost:3306/metropolis_db";
    public static final String username = "root";
    public static final String password = "Akkdzidzi100!";


public static String getUrl() {
        return url;
    }
    public static String getUserName() {
        return username;
    }
    public static String getPassword() {
        return password;
    }
}
