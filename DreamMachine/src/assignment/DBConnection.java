package assignment;

import java.sql.*;

/**
 * This class functions as a singleton accessory for executing operations on the
 * Quiz database. This should be initialized in ServletContext once
 */
public class DBConnection {

    private static Connection con;
    
    /**
     * Connects to the database specified in the DBInfo file. TODO: decide when
     * to connect, how long one connection should last, and when to time out
     * connection.
     * (provided utility)
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
     * (provided utility)
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
     * (provided utility)
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
     * Executes the query stored in the prepared statement along the current
     * connection. This returns the result set of the execution.
     * (provided utility)
     * @param query the query to be sent to the SQL database
     * @return the result set of the database query
     */
    public static ResultSet query(PreparedStatement stmt) {
        try {
        	return stmt.executeQuery();
        } catch(SQLException ignored) {
            return null;
        }
    }
    
	/**
	 * Basic update query to update information in the connected database.
	 * (provided utility)
	 * @param update the update string that will be called (not secure from
	 *        SQL injection)
	 * @return the id of the object if it exists of the update (Insert, Delete, Update)
	 */
	public static int update(String update) {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);
			ResultSet keys = stmt.getGeneratedKeys();
			if (!keys.first())
				return 0;
			return keys.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Basic update query to update information in the connected database.
	 * (provided utility)
	 * @param update the update string that will be called (not secure from
	 *        SQL injection)
	 * @return the id of the object if it exists of the update (Insert, Delete, Update)
	 */
	public static int update(PreparedStatement stmt) {
		try {
			stmt.executeUpdate();
			ResultSet keys = stmt.getGeneratedKeys();
			if (!keys.first())
				return 0;
			return keys.getInt(1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Fetch a prepared statement given an sql query string. This statement
	 * may have values inserted and get executed later.
	 * (provided utility)
	 * @return prepared statement to execute later
	 */
	public static PreparedStatement beginStatement(String query) {
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void printDatabase () {
		String query_string = "SELECT * FROM achievements";
		ResultSet rs = query(query_string);
		try {
			while (rs.next()){
				System.out.println("User ID: " + rs.getInt("user_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
