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
	
	public static double averageScore(List<Score> allScores ) {
		if (allScores.isEmpty()) return 0;
		int sum = 0; 
		for (Score score: allScores) {
			sum+= score.score; 
		}
		return (double)sum/allScores.size();
	}
	
	public static int highScore(List<Score> allScores ) {
		if (allScores.isEmpty()) return 0;
		Collections.sort(allScores, new Score.ScoreComparator());
		return allScores.get(0).score; 
	}
	
	public static int lowScore(List<Score> allScores ) {
		if (allScores.isEmpty()) return 0;
		Collections.sort(allScores, new Score.ScoreComparator());
		return allScores.get(allScores.size()-1).score; 
	}
	

	
	public static ArrayList<Score> pastPerformances(int user_id, int quiz_id) {
		ArrayList<Score> allUserScores = new ArrayList<Score>();

		String queryStr = "SELECT * FROM scores WHERE quiz_id=" + quiz_id + " AND user_id=" + user_id;
		ResultSet rs = DBConnection.query(queryStr);
		if (rs == null) return allUserScores;
		try {
			while (rs.next())
				allUserScores.add(new Score(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Collections.sort(allUserScores, new Score.DateComparator());
		return allUserScores; 
	}
	
	public static ArrayList<Score> highestPerformers(List<Score> allScores ) {
		if (allScores.isEmpty()) return new ArrayList <Score>();

		Collections.sort(allScores, new Score.ScoreComparator());
		int maxIndex = allScores.size() > MAX_HIGH_PERFORMERS ? MAX_HIGH_PERFORMERS-1 : allScores.size()-1;
		return new ArrayList <Score>(allScores.subList(0, maxIndex));
	}
	
	@SuppressWarnings("deprecation")
	public static ArrayList<Score> highestPerformersPastDay(List<Score> allScores ) {
		if (allScores.isEmpty()) return new ArrayList <Score>();

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
		if (allScores.isEmpty()) return new ArrayList <Score>();

		Collections.sort(allScores, new Score.DateComparator());
		int maxIndex = allScores.size() > MAX_RECENT_PERFORMANCES ? MAX_RECENT_PERFORMANCES-1 : allScores.size()-1;
		return new ArrayList <Score>(allScores.subList(0, maxIndex));
	}
}