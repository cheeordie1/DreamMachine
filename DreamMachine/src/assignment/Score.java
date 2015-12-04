package assignment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Score {
	public int score; 
	public Date startTime;
	public Date finishTime;
	public int user_id;
	public User user; 
	
	//for debugging
	public Score(int score, Date startTime, Date finishTime, int user_id, String username) {
		this.score = score;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.user_id = user_id; 
		this.user = new User(username);
	}

	public Score(ResultSet set) throws SQLException {
		user_id = set.getInt("user_id");
		user = new User(DBConnection.query("SELECT * FROM users WHERE user_id =" + user_id));
		score = set.getInt("score");
		startTime = set.getDate("start_time");
		finishTime = set.getDate("finishTime");
	}
	
	
	public long getDuration() {
		return (finishTime.getTime() - startTime.getTime())/1000;
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
