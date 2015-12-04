package assignment;

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
	public static final int NONE = -1;
	public static final String NONE_STRING = "none";
	
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
	List<Friend> friends = new ArrayList<Friend>();
	String query = "SELECT * FROM " + TABLE_NAME + 
                   " WHERE (sender = " + user_id + 
                   " OR reciever = " + user_id + 
                   ") AND status = " + status; 
	ResultSet rs = DBConnection.query(query);
	if (rs == null) return friends;
	try {
		while (rs.next()){
			friends.add(new Friend(rs));
		}
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return friends;
}
	
	/**
	 * Search the database friend
	 * @param user_id The id of the user to find friends of
	 * @return a list of the id's of the friends of the user 
	 * associated with the user_id
	 */
	public static List<Integer> getFriends (int user_id) {
    	List<Integer> friends = new ArrayList<Integer>();
		String query = "SELECT * FROM " + TABLE_NAME + 
	               " WHERE (sender = " + user_id + 
	               " OR reciever = " + user_id + 
	               ") AND status = " + ACCEPTED; 
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return friends;
    	try {
			while (rs.next()){
				if (rs.getInt("sender") == user_id){
					friends.add(rs.getInt("reciever"));
				} else {
					friends.add(rs.getInt("sender"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return friends;
	}
	
	/**
	 * Search the database friend for pending requests
	 * @param user_id The id of the user to find pending requests of
	 * @return a list of the id's of the pending requests of the user
	 * associated with the user_id, all pending requests (ones they have
	 * sent and ones received)
	 */
	public static ArrayList <Integer> getAllPendingRequests (int user_id) {
		ArrayList<Integer> pending = new ArrayList<Integer>();
		String query = "SELECT * FROM " + TABLE_NAME + 
						" WHERE (sender = " + user_id + 
	               		" OR reciever = " + user_id +  
	               		") AND status = " + PENDING;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return pending;
		try {
			while (rs.next()) {
					if (rs.getInt("sender") == user_id) {
						pending.add(rs.getInt("reciever"));
					} else {
						pending.add(rs.getInt("sender"));
					}
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pending;
	}
	
	/**
	 * Search the database for friends requests send to a user
	 * @param user_id The id of the user to find all the requests of
	 * @return a list of the id's of the people who have sent that user 
	 * a friend request
	 */
	public static ArrayList <Integer> getFriendRequests (int user_id) {
		ArrayList<Integer> pendingRequests = new ArrayList<Integer>();
		String query = "SELECT * FROM " + TABLE_NAME + 
						" WHERE reciever = " + user_id + 
						" AND status = " + PENDING;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return pendingRequests;
		try {
			while (rs.next()) {
				pendingRequests.add(rs.getInt("sender"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pendingRequests;
	}
	
	/**
	 * Search the database friend for blocked requests
	 * @param user_id The id of the user to find pending requests of
	 * @return a list of the id's of the pending requests of the user
	 * associated with the user_id
	 */
	
	public static List <Integer> getBlockedFriends (int user_id) {
		List<Integer> blocked = new ArrayList<Integer>();
		String query = "SELECT * FROM " + TABLE_NAME + 
	               " WHERE (sender = " + user_id + 
	               " OR reciever = " + user_id + 
	               ") AND status = " + BLOCKED;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return blocked;
		try {
			while (rs.next()) {
				if (rs.getInt("sender") == user_id) {
					blocked.add(rs.getInt("reciever"));
				} else {
					blocked.add(rs.getInt("sender"));
				}
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return blocked;
	}
	
	/** 
	 * Adds a friendships into the database and sets it to pending
	 * @param user_id_A The id of the first user in the friendship (sender)
	 * @param user_id_B The id of the second user in the friendship (reciever)
	 */

	public static void sendFriendRequest (int sender, int reciever) {
		String entry = "INSERT INTO " + TABLE_NAME + "(sender, reciever, status) VALUES ('"
						+ sender + "', '" + reciever + "', '" + PENDING + "')";
		int result = DBConnection.update(entry);
	}
	
	/**
	 * Updates a friendships from the database from pending to choice of user
	 * @param user_id_A The id of the first user in the friendship
	 * @param user_id_B The id of the second user in the friendship
	 * @param decision The decision (accepted, denied, blocked) that the user chose
	 */
	public static void updateFriendRequest (int sender, int reciever, int decision) {
		String entry = "UPDATE " + TABLE_NAME + " SET status = " 
					   + decision + " WHERE sender = " + sender 
					   + " AND reciever = " + reciever;
		DBConnection.update(entry);
	}
	
	/**
	 * Returns the status of the friendship between two users
	 * @param friend_a The id of the first user in the friendship
	 * @param friend_b The id of the second user in the friendship
	 * @return Returns the status of the friendship, if the friendship 
	 * does not exist or some error occurs, it returns -1
	 */
	public static int getStatusOfFriendship (int friend_a,  int friend_b) {
		String query = "SELECT * FROM " + TABLE_NAME + 
					   " WHERE (sender = " + friend_a +
					   " AND reciever = " + friend_b + 
					   ");";
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return NONE;
		try {
			while (rs.next()) {
				return rs.getInt("status");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		String query_flipped = "SELECT * FROM " + TABLE_NAME + 
				   " WHERE (sender = " + friend_b +
				   " AND reciever = " + friend_a + 
				   ");";
		ResultSet rs_flipped = DBConnection.query(query_flipped);
		try {
			while (rs_flipped.next()) {
				return rs_flipped.getInt("status");
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return NONE;
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
			case NONE:
			default:
				statusString = NONE_STRING;
				break;
		}
	}
}