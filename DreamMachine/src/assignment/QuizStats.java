package assignment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizStats {
	
	private static final int MAX_HIGH_PERFORMERS = 5;
	private static final int MAX_RECENT_PERFORMANCES = 7;
	
	
	public static int timesPlayed(List<Score> allScores ) {
		return allScores.size(); 
	}
	
	public static int averageScore(List<Score> allScores ) {
		int sum = 0; 
		for (Score score: allScores) {
			sum+= score.score; 
		}
		return sum/allScores.size();
	}
	
	public static int highScore(List<Score> allScores ) {
		Collections.sort(allScores, new Score.ScoreComparator());
		return allScores.get(0).score; 
	}
	
	public static int lowScore(List<Score> allScores ) {
		Collections.sort(allScores, new Score.ScoreComparator());
		return allScores.get(allScores.size()-1).score; 
	}
	
	public static ArrayList<Score> pastPerformances(int user_id, int quiz_id) {
//		String query = "SELECT * FROM scores WHERE quiz_id=" + quiz_id + " AND user_id=" + user_id;
//		ResultSet UserScoreSet = DBConnection.query(query);		
		ArrayList<Score> allUserScores = new ArrayList<Score>();
//		try {
//			while(UserScoreSet.next()) allUserScores.add(new Score(UserScoreSet));
//		} catch (SQLException e) {}	

		allUserScores.add(new Score(10, new Date((long)1000),new Date((long) 100000), 1, "mark"));
		allUserScores.add(new Score(20, new Date((long)1000),new Date((long) 100000), 1, "mark"));
		allUserScores.add(new Score(30, new Date((long)1000),new Date((long) 100000), 1, "mark"));
		allUserScores.add(new Score(40, new Date((long)1000),new Date((long) 100000), 1, "mark"));
		
		Collections.sort(allUserScores, new Score.DateComparator());
		return allUserScores; 
	}
	
	public static ArrayList<Score> highestPerformers(List<Score> allScores ) {
		Collections.sort(allScores, new Score.ScoreComparator());
		int maxIndex = allScores.size() > MAX_HIGH_PERFORMERS ? MAX_HIGH_PERFORMERS-1 : allScores.size()-1;
		return new ArrayList <Score>(allScores.subList(0, maxIndex));
	}
	
	@SuppressWarnings("deprecation")
	public static ArrayList<Score> highestPerformersPastDay(List<Score> allScores ) {
		Collections.sort(allScores, new Score.DateComparator());
		ArrayList <Score> highestPerformers = new ArrayList <Score>(); 
		Date date = new Date(Instant.now().toEpochMilli());

		int index = 0; 
		while (index < allScores.size() && highestPerformers.size() < MAX_RECENT_PERFORMANCES) {
			Score score = allScores.get(index);
			if (score.finishTime.getDay() == date.getDay() &&
				score.finishTime.getMonth() == date.getMonth() &&
				score.finishTime.getYear() == date.getYear()) {
	
				highestPerformers.add(score); 
			}
			index++;
		}
		return highestPerformers; 
	}
	
	public static ArrayList<Score> recentPerformances(List<Score> allScores ) {
		Collections.sort(allScores, new Score.DateComparator());
		int maxIndex = allScores.size() > MAX_RECENT_PERFORMANCES ? MAX_RECENT_PERFORMANCES-1 : allScores.size()-1;
		return new ArrayList <Score>(allScores.subList(0, maxIndex));
	}
}
