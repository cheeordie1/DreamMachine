package assignment;

import java.sql.ResultSet;


public class Challenges {
	
	public static final String TABLE_NAME = "challenges";
	
	/**
	 * Searches the database for a user and returns the challenged 
	 * quizzes for that user
	 * @param user_id The id of the user whom we are searching for
	 * @return Returns a list of links to quizzes that the particular
	 * user was challenged to take
	 */
	public static ResultSet getChallengedQuizzes (int user_id) {
		String query = "SELECT * FROM " + TABLE_NAME + 
					   " WHERE receiver_user_id = " + user_id;
		ResultSet rs = DBConnection.query(query);
		return rs;
	}
	
	/**
	 * Deletes a given challenge from the database
	 * @param challenging_id The id for the specific challenge
	 * that will be deleted from the database
	 */
	public static void destroy(int challenge_id) {
		String query = "DELETE FROM " + TABLE_NAME + 
					   " WHERE challenge_id = " + challenge_id;
		DBConnection.update(query);
	}
	
	/**
	 * Saves a challenge into the challenges database
	 * @param sender The id of the user who is sending the challenge
	 * @param receiver The id fo the user who is receiving the challenge
	 * @param link The link of the quiz that is being challenged
	 * @param best_score The best score of the person sending the challenge on 
	 * that particular quiz
	 */
	public static void save (int sender, int receiver, String link) {
		String entry = "INSERT INTO " + TABLE_NAME + "(sender_user_id, receiver_user_id, link) VALUES ('"
						+ sender + "', '" + receiver + "', '" + link + "')";
		DBConnection.update(entry);
	}
	
//	/*
//	 * written only for testing purposes
//	 */
//	public static void main (String args []) {
//		DBConnection.connect();
//		String query = "SELECT * FROM challenges";
//		ResultSet rs = DBConnection.query(query);
//		try {
//			while (rs.next()){
//				System.out.println("SENDER: " + rs.getInt("sender") + " RECIEVER: " + rs.getInt("receiver") + 
//						"QUIZ: " + rs.getString("link"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
}









