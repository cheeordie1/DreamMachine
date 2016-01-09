package assignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Friend {
	// instance variables
	public String statusString;
	
	// database variables
	public int friend_id;
	public int sender;
	public int receiver;
	public int status;
	public Date created_at;
	
	// static variables
	public static final String TABLE_NAME = "friends";
	public static final int PENDING = 0;
	public static final String PENDING_STRING = "pending";
	public static final int ACCEPTED = 1;
	public static final String ACCEPTED_STRING = "accepted";
	public static final int DECLINED = 2;
	public static final String DECLINED_STRING = "declined";
	public static final int BLOCKED = 3;
	public static final String BLOCKED_STRING = "blocked";
	
	public Friend() {
	}
	
	public Friend(ResultSet rs) {
		try {
			friend_id = rs.getInt("friend_id"); 
			sender = rs.getInt("sender");
			receiver = rs.getInt("receiver");
			status = rs.getInt("status");
			created_at = rs.getDate("created_at");
			storeStatusString();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Insert the friend into the friends table and assign the 
	 * friend_id instance variable to the unique id generated by 
	 * adding the friend to the table.
	 */
	public boolean save() {
		boolean errors = false;
		String update = "INSERT INTO " + TABLE_NAME + 
				        "(sender,receiver,status) VALUES(?,?,?)";
		PreparedStatement stmt = DBConnection.beginStatement(update);
		try {
			stmt.setInt(1, sender);
			stmt.setInt(2, receiver);
			stmt.setInt(3, status);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
		friend_id = DBConnection.update(stmt);
		if (friend_id == 0) return false;
		return true; 
	}
	
	/**
	 * Update the values in the database for a given friendship
	 * @return true if update succeeds
	 */
	public boolean update() {
		if (friend_id == 0) return false;
		String query = "UPDATE " + TABLE_NAME + 
				       " SET status='" + status + "'" +
				       " WHERE friend_id = " + friend_id;
		DBConnection.update(query);
		return true;
	}
	
	/**
	 * Delete a friend entry from the friends table
	 * @return true if the delete works, false otherwise
	 */
	public boolean delete() {
        if (friend_id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE friend_id = '" + friend_id + "' LIMIT 1";
        DBConnection.update(query);
        return true;
	}
	
	/**
	 * Search the database for all friendships containing user_id
	 * @param user_id The id of the user
	 * @return a list of friendships from database
	 */
	public static List<Friend> searchByUserID (int user_id) {
    	return searchByUserIDStatus(user_id, ACCEPTED);
	}
	
	/**
	 * Search the database for friendships with user id and that has the
	 * specified status.
	 * @param user_id the id of the user to search by
	 * @param status the status to match
	 * @return list of friendships that meet specifications
	 */
	public static List<Friend> searchByUserIDStatus(int user_id, int status) {
		String query = "SELECT * FROM " + TABLE_NAME + 
					   " WHERE (sender = '" + user_id + "'" + 
					   " OR receiver = '" + user_id + "'" +
					   ") AND status = '" + status + "'"; 
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search the database for a friendship between two users.
	 * @param user_id_a one user in the friendship
	 * @param user_id_b the other user in the friendship
	 * @return a list of friendships with the two users
	 */
	public static List<Friend> searchByUserIDUserID(int user_id_a, int user_id_b) {
		String query = "SELECT * FROM " + TABLE_NAME + 
				       " WHERE (sender = '" + user_id_a + "' " + 
				       "AND receiver = '" + user_id_b + "') " +
				       "OR (sender = '" + user_id_b + "' " + 
				       "AND receiver = '" + user_id_a + "')";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Updates a friendships from the database from pending to choice of user
	 * @param user_id_A The id of the first user in the friendship
	 * @param user_id_B The id of the second user in the friendship
	 * @param decision The decision (accepted, denied, blocked) that the user chose
	 */
	public static void updateFriendRequest (int sender, int receiver, int decision) {
		String entry = "UPDATE " + TABLE_NAME + " SET status = " 
					   + decision + " WHERE sender = " + sender 
					   + " AND receiver = " + receiver;
		DBConnection.update(entry);
	}
	
	public void storeStatusString() {
		switch(status) {
			case PENDING:
				statusString = PENDING_STRING;
				break;
			case ACCEPTED:
				statusString = ACCEPTED_STRING;
				break;
			case DECLINED:
				statusString = DECLINED_STRING;
				break;
			case BLOCKED:
				statusString = BLOCKED_STRING;
				break;
		}
	}
	
	/**
	 * Returns a list of friends from the result set. Could be empty
	 * @return List of friends from the result set
	 */
	private static List<Friend> fromResultSet(ResultSet rs) {
		List<Friend> friends = new ArrayList<Friend>();
		if (rs == null) return friends;
		try {
			while (rs.next())
				friends.add(new Friend(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friends;
	}
}