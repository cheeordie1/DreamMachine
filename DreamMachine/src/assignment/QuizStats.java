package assignment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;

public class QuizStats {
	
	//All of the scores for the quiz
	private ArrayList<Score> allScores;
	
	private static final int MAX_HIGH_PERFORMERS = 5;
	private static final int MAX_RECENT_PERFORMANCES = 7;
	
	
	public QuizStats(){}
	
	
	public void popAllScores(int quiz_id) {
		String query = "SELECT * FROM scores";
		ResultSet rs = DBConnection.query(query);
		try {
			while(rs.next()) allScores.add(new Score(rs));
		} catch (SQLException e) {}
	}
	
	public int timesPlayed() {
		return allScores.size(); 
	}
	
	public int averageScore() {
		int sum = 0; 
		for (Score score: allScores) {
			sum+= score.score; 
		}
		return sum/allScores.size();
	}
	
	public int highScore() {
		Collections.sort(allScores, new Score.ScoreComparator());
		return allScores.get(0).score; 
	}
	
	public int lowScore() {
		Collections.sort(allScores, new Score.ScoreComparator());
		return allScores.get(allScores.size()-1).score; 
	}
	
	public ArrayList<Score> pastPerformances(int user_id, int quiz_id) {
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
	
	public ArrayList<Score> highestPerformers() {
		Collections.sort(allScores, new Score.ScoreComparator());
		return new ArrayList <Score>(allScores.subList(0, MAX_HIGH_PERFORMERS));
	}
	
	@SuppressWarnings("deprecation")
	public ArrayList<Score> highestPerformersPastDay() {
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
		}
		return highestPerformers; 
	}
	
	public  ArrayList<Score> recentPerformances() {
		Collections.sort(allScores, new Score.DateComparator());
		return new ArrayList <Score>(allScores.subList(0, MAX_RECENT_PERFORMANCES));
	}
}
