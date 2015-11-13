package assignment;

import java.sql.*;
import java.util.*;

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
     * @return the resulting success of establishing a connection
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
     * Deletes from the given table the entry specified by the given uid. 
     * @param table the table to delete the entry from
     * @param pid the unique identifier used to search for the entry
     */
    public static boolean deleteUpdate(String table, int pid) {
        try {
            Statement stmt = null;
    
            /* create deletion update */
            String deleteEntry = "DELETE FROM " + table + " WHERE pid = " + pid;
            System.out.println(deleteEntry);
            stmt = con.createStatement();
            stmt.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);
            stmt.executeUpdate(deleteEntry); 
            return true;
        } catch (SQLException ignored) {
            return false;    
        }
    }

    /**
     * Executes the given insert sql update over the DB connection.
     * @param sql the sql string used for the insert execution
     * @return the unique identifier created for this entry
     */
    public static int insertUpdate(String sql) {
        try {

            ResultSet rs = null;
            Statement stmt = null;

            /* create the new MySQL entry */
            stmt = con.createStatement();
            stmt.executeQuery("USE " + DBInfo.MYSQL_DATABASE_NAME);
            stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            
            /* return the unique uid */
            rs = stmt.getGeneratedKeys();
            if(rs.next()) return rs.getInt(1);

        } catch (SQLException ignored) {}
        return 0;
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
