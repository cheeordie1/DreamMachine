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
  		    query("USE " + DBInfo.MYSQL_DATABASE_NAME);
            return true;
        } catch(SQLException ignored) {
            return false;
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: Specified class doesn't exist");
            e.printStackTrace();
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
        } catch(SQLException ignored) {
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
            return stmt.executeQuery(query);
        } catch(SQLException ignored) {
            return null;
        }
    }

	/**
	 * Basic update query to update information in the connected database.
	 * (provided utility)
	 * @param update the update string that will be called (not secure from
	 *        SQL injection)
	 * @return the result set of the update (Insert, Delete, Update)
	 */
	public static ResultSet update(String update) {
		try {
		  Statement stmt = con.createStatement();
		  stmt.execute(update);
		  return stmt.getResultSet();
		} catch(SQLException e) {
		  e.printStackTrace();
		}
		return null;
	}

}
