package assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Achievements {
	
	private static final int AMATEUR_AUTHOR = 1; 
	private static final int PROLIFIC_AUTHOR = 2; 
	private static final int PRODIGIOUS_AUTHOR = 3; 
	private static final int AMATEUR_QUIZZER = 4; 
	private static final int PROLIFIC_QUIZZER = 5; 
	private static final int PRODIGIOUS_QUIZZER = 6; 
	private static final int BASIC_BUDDY = 7; 
	private static final int SOCIAL_BUTTERFLY = 8;
	private static final int THE_GREATEST = 9; 

	private static final int AMATEUR_COUNT = 1;
	private static final int PROLIFIC_COUNT = 5;
	private static final int PRODIGIOUS_COUNT = 10;

	
	private static int getAuthoredCount(int user_id) throws SQLException{
		String queryStr = "SELECT COUNT(*) FROM quizzes WHERE user_id=" +user_id+ ";";
		ResultSet result = DBConnection.query(queryStr);
		return result.getInt(1);
	}
	
	private static int getQuizCount(int user_id) throws SQLException{
		String queryStr = "SELECT COUNT(*) FROM scores WHERE user_id=" +user_id+ ";";
		ResultSet result = DBConnection.query(queryStr);
		return result.getInt(1);
	}
	
	private static int getFriendCount(int user_id) throws SQLException{
		String queryStr1 = "SELECT COUNT(*) FROM quizzes WHERE friendA=" +user_id+ ";";
		ResultSet result1 = DBConnection.query(queryStr1);
		
		String queryStr2 = "SELECT COUNT(*) FROM quizzes WHERE friendB=" +user_id+ ";";
		ResultSet result2 = DBConnection.query(queryStr2);
		
		return result1.getInt(1) + result2.getInt(1);
	}
	
	private static void updateTable(String a, int user_id) {
		String entryStr = "UPDATE achievements SET " + a + " = TRUE WHERE user_id = " + user_id +";";
		DBConnection.update(entryStr);
	}
	
	private static boolean checkTable(String a, int user_id) {
 		String queryStr = "SELECT COUNT(*) FROM achievements WHERE user_id=" + user_id + " AND " + a + "=TRUE;";
 		System.out.println(queryStr);

		int numOcc = -1;
		try {
			ResultSet set = DBConnection.query(queryStr);
			numOcc = set.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return numOcc == 1; 
	}
	
	//Check to see if the achievement is accomplished. Will update to accomplished if necessary
	public static boolean achievementAccomplished(int achievementNum, int user_id) {
		if (achievementNum < AMATEUR_AUTHOR || achievementNum > THE_GREATEST) return false;
		String tableRowName = "a" + achievementNum; 

			if (checkTable(tableRowName, user_id) == true) return true;
		
		try {
			boolean update = false; 
			
			switch (achievementNum) {
				case AMATEUR_AUTHOR:
					update = getAuthoredCount(user_id) >= AMATEUR_COUNT;
					break;
				case PROLIFIC_AUTHOR:
					update = getAuthoredCount(user_id) >= PROLIFIC_COUNT;
					break; 
				case PRODIGIOUS_AUTHOR:
					update = getAuthoredCount(user_id) >= PRODIGIOUS_COUNT;
					break; 	
				case AMATEUR_QUIZZER:
					update = getQuizCount(user_id) >= AMATEUR_COUNT;
					break; 
				case PROLIFIC_QUIZZER:
					update = getQuizCount(user_id) >= PROLIFIC_COUNT;
					break; 
				case PRODIGIOUS_QUIZZER:
					update = getQuizCount(user_id) >= PRODIGIOUS_COUNT;
					break; 
				case BASIC_BUDDY:
					update = getFriendCount(user_id) >= AMATEUR_COUNT;
					break; 
				case SOCIAL_BUTTERFLY:
					update = getFriendCount(user_id) >= PRODIGIOUS_COUNT;
					break; 
				//Must manually update
				case THE_GREATEST: 
					break; 	
			}
			
			if (update) updateTable(tableRowName, user_id);
			return update;
			
		} catch (SQLException e) {
			return false;	
		}	
	}
	
	//Manually sets achievement as accomplished
	public boolean manuallySetAchievementTrue(int achievementNum, int user_id){
		updateTable("a"+achievementNum, user_id);
		return true; 
	}
	
	//Returns ints corresponding with every achievement user has
	public ArrayList<Integer> getUserAchievements(int user_id) {
		String queryStr = "SELECT * FROM quizzes WHERE user_id=" +user_id+ ";";
		ResultSet result = DBConnection.query(queryStr);
		ArrayList<Integer> achievements = new ArrayList<Integer>();
		
		for (int i = AMATEUR_AUTHOR; i <= THE_GREATEST; i++) {
			try {
				if (result.getBoolean("a"+i)) achievements.add(Integer.valueOf(i));
			} catch (SQLException e) {}
		}
		return achievements;	
	}
	
	
	//
	public static void main(String[] args) {
		DBConnection.connect();
		DBConnection.printDatabase();
		
		System.out.println(achievementAccomplished(11, 1));
		System.out.println("hello");
	}

}












