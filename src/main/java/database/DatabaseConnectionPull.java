package database;

public class DatabaseConnectionPull {
    private static String url;
    private static String userName;
    private static String password;

    public DatabaseConnectionPull(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
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
