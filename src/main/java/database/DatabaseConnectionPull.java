package database;

public final class DatabaseConnectionPull {
    private static String url = "jdbc:mysql://localhost:3306/gmzar23";
    private static String userName = "root";
    private static String password = "Bozartma";

    private DatabaseConnectionPull() {}

    public static void initialize(String url, String userName, String password) {
        DatabaseConnectionPull.url = url;
        DatabaseConnectionPull.userName = userName;
        DatabaseConnectionPull.password = password;
    }

    public static String getUrl() {
        return url;
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }
}