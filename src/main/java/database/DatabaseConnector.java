<<<<<<<< HEAD:src/main/java/classes/DataBases/DatabaseConnector.java
package classes.DataBases;
========
package database;
>>>>>>>> df1a6eb3c0b7ed44c13ce04e76d91a2b760b57ed:src/main/java/database/DatabaseConnector.java

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