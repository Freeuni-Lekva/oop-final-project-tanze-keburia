package database.database_connection;

public class DatabaseConnectionPool {
    public static final String url = "jdbc:mysql://localhost:3306/gmzar23";
    public static final String username = "root";
    public static final String password = "Bozartma";


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
