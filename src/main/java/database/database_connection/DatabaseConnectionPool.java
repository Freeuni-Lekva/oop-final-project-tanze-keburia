package database.database_connection;

public class DatabaseConnectionPool {
    public static final String url = "jdbc:mysql://localhost:3306/metro";
    public static final String username = "icosahedron";
    public static final String password = "Loko_kina1";


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
