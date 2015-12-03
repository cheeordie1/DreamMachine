package assignment;

import java.util.ArrayList;

public class QuizStatistics {
	
	private static final int MAX_HIGH_PERFORMERS = 5;
	private static final int MAX_RECENT_PERFORMANCES = 7;
	
	
/*	dont need.
	*Title
	 * 
	 need
	 highest performers
	 top performance of past day
	 recent performance
	 users past performance
	 summary statistics of all users performances
	 */
	
	/* Returns an ArrayList of the top performing users on this particular quiz.
	 * 
	 * implementation: 
	 * Queries score table for scores associated with quiz, in order of highest
	 * scores to lowest. Then extract MAX_HIGH_PERFORMERS unique user_id's from the 
	 * results.Then populate ArrayList of Users with ids.
	 * 
	 */
	public static ArrayList<User> highestPerformers(int quiz_id) {

		return new ArrayList<User>();
	}
	
	/* Returns an ArrayList of the top performers of the past day.
	 * 
	 * implementation: 
	 * Queries score table for scores associated with quiz and where timestamp is 
	 * within last day, in order of highest scores to lowest. Then populate Array
	 * List of Users with ids.
	 */
	public static ArrayList<User> highestPerformersPastDay(int quiz_id) {
		return new ArrayList<User>();
	}
	
	
	/* implementation:
	 * query score table for scores associated with quiz and in order of most recent
	 * date to oldest. Then extract the user and score and shove them in.
	 */
	public static ArrayList<User_Score> recentPerformances(int quiz_id) {
		return new ArrayList<User_Score>();
	}
	
	
	public class User_Score {
		User user; 
		int score; 
	}
	
	/* implementation:
	 * query scores table for scores associated with quiz_id and user_id in order
	 * of latest date to oldest. Then extract the first MAX_RECENT_PERFORMANCES 
	 * scores.
	 */
	public static ArrayList<Integer> pastPerformances(int quiz_id, int user_id) {
		return new ArrayList<Integer>();
	}
	
	/* implementation: 
	 * calculates the flippin average score and stuff...i dunno.
	 */
	public static ArrayList<Integer> performanceStatistics(int quiz_id) {
		return new ArrayList<Integer>();
	}

}
