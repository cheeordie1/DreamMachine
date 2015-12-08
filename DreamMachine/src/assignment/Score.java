package assignment;

import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Score {
	public int score; 
	public Timestamp startTime;
	public Timestamp finishTime;
	public int user_id;
	public int quiz_id; 
	public User user; 
	
	//for debugging
	public Score(int score, Timestamp startTime, Timestamp finishTime, int user_id, String username, int quiz_id) {
		this.score = score;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.user_id = user_id; 
		this.user = new User(username);
		this.quiz_id = quiz_id; 
	}

	public Score(ResultSet set) throws SQLException {
		user_id = set.getInt("user_id");
		
		//user = new User(DBConnection.query("SELECT * FROM users WHERE user_id =" + user_id));
	
		quiz_id = set.getInt("quiz_id");

		score = set.getInt("score");
		startTime = set.getTimestamp("start_time");
		finishTime = set.getTimestamp("finish_time");
	}
	
	/**
	 * Search for a quiz by its unique quiz_id
	 * @param quiz_id the id to search by
	 * @return the list of quizzes with the same id
	 */
	public static List<Score> searchByQuizID(int quiz_id) {
		List<Score> allScores = new ArrayList<Score>();
		String query = "SELECT * FROM scores WHERE quiz_id = " + quiz_id;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return allScores;
		try {
			while (rs.next())
				allScores.add(new Score(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allScores;
	}
	
	/**
	 * Search for a quiz by its unique quiz_id
	 * @param quiz_id the id to search by
	 * @return the list of quizzes with the same id
	 */
	public static List<Score> searchByUserID(int user_id) {
		List<Score> allScores = new ArrayList<Score>();
		
		String query = "SELECT * FROM scores WHERE user_id = " + user_id;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return allScores;
		try {
			while (rs.next())
				allScores.add(new Score(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allScores;
	}
	
	
	public long getDuration() {
		return (finishTime.getTime() - startTime.getTime());
	}
    
	public static class ScoreComparator implements java.util.Comparator<Score>
	 {
		public int compare(Score s1, Score s2) {
			if (s1.score == s2.score) 
				return (int) (s2.getDuration() - s1.getDuration());
			return s2.score - s1.score;
		}
	 }

	public static class DateComparator implements java.util.Comparator<Score>
	 {
		public int compare(Score s1, Score s2) {
			if (s1.finishTime.equals(s2.finishTime)){
				if (s1.score == s2.score) 
					return (int) (s2.getDuration() - s1.getDuration());
				return s2.score - s1.score;
			} else if (s1.finishTime.after(s2.finishTime)) {
				return 1;
			} else return -1; 
		}
	 }
}