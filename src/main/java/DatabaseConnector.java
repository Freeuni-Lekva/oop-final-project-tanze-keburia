

import java.sql.*;

public class DatabaseConnector {
    private String dbUrl;
    private String dbUser;
    private String dbPassword;
    private static DatabaseConnector dbc;

   private DatabaseConnector(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {

       if(dbc == null) {
           Class.forName("com.mysql.cj.jdbc.Driver");
           dbc = this;
           dbc.dbUrl = dbUrl;
           dbc.dbUser = dbUser;
           dbc.dbPassword = dbPassword;
       }
    }

    public static DatabaseConnector getInstance(String dbUrl, String dbUser, String dbPassword) throws ClassNotFoundException {
       DatabaseConnector dbc = new DatabaseConnector(dbUrl, dbUser, dbPassword);
       return dbc;
    }


    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(dbc.dbUrl, dbc.dbUser, dbc.dbPassword);
            return connection;
        } catch (SQLException e) {
            throw e;
        }

    }


}