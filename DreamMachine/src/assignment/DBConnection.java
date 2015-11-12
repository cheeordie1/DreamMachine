package assignment;

import java.sql.*;

/**
 * This class functions as a singleton accessor for executing operations on the
 * Quiz database. This should be initialized in ServletContext once
 */
public class DBConnection {

    private static Connection con;
    
    /**
     * Connects to the database specified in the DBInfo file. TODO: decide when
     * to connect, how long one connection should last, and when to time out
     * connection.
     * @return the resulting success of establishing a connection.
     */
    public static boolean connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection(
                "jdbc:mysql://" + DBInfo.MYSQL_DATABASE_SERVER,
                DBInfo.MYSQL_USERNAME,
                DBInfo.MYSQL_PASSWORD);
            return true;
        } catch (SQLException ignored) {
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Specified class doesn't exist");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Executes the given query along the connection. This returns the result
     * set of the query, or null if the query failed.
     * @param query the query to be sent to the SQL database
     * @return the result set of the database query
     */
    public static ResultSet query(String query) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);
            return stmt.executeQuery(query);
        } catch (SQLException ignored) {
            return null;
        }
    }

    /**
     * Executes the given update along the connection. This returns true when
     * the update succeeds in the database specified by the DBInfo file, false
     * otherwise.
     * @param query the query to be sent to the SQL database
     * @return the resulting success of the update call 
     */
    public static boolean update(String update) {
        try {
            Statement stmt = con.createStatement();
            stmt.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);
            stmt.executeUpdate(update);
            return true;
        } catch (SQLException ignored) {
            return false;
        }
    }
    
    /**
     * Closes the connection to the database.
     * @return the resulting success of the update
     */
    public static boolean closeConnection() {
        try {
            con.close();
            return true;
        } catch (SQLException ignored) {
            return false;
        }
    }
}
