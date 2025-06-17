import java.sql.*;

public class DatabaseConnector {
    private static String dbUrl;
    private static String dbUser;
    private static String dbPassword;
    private static boolean created = false;
    private static DatabaseConnector dbc;

   private DatabaseConnector(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbc.dbUrl = dbUrl;
        dbc.dbUser = dbUser;
        dbc.dbPassword = dbPassword;
        created = true;
    }

    public static DatabaseConnector getInstance(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {
       if (created) return dbc;
       return (new DatabaseConnector(dbUrl, dbUser, dbPassword).dbc);
    }


    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            return connection;
        } catch (SQLException e) {
            throw e;
        }
    }


}