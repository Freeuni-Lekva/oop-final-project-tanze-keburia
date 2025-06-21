package database;

public class DatabaseConnectionPull {
    public static final String url = "jdbc:mysql://localhost:3306/g";
    public static final String username = "root";
    public static final String password = "gzobava2005";


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
