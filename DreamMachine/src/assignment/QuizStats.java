package assignment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizStats {
	
	private static final int MAX_HIGH_PERFORMERS = 5;
	private static final int MAX_RECENT_PERFORMANCES = 7;
	
	public Date date;
	public int timesPlayed;
	public int averageScore;
	public int highScore;
	public int lowestScore; 
	ArrayList<Score> allScore;
		
	QuizStats(int quiz_id){
		
		ResultSet set = DBConnection.query("SELECT * FROM scores WHERE quiz_id =" + quiz_id);
		ArrayList<Score> allScore = new ArrayList<Score>();
		
		try {
			while(set.next()) {
				int user_id = set.getInt("user_id");
				int score = set.getInt("score");
				int 
				
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		
		date = new Date(1, 2, 3);
		timesPlayed = 2;
		averageScore = 3; 
		highScore = 4; 
		lowestScore = 6; 
	}
	

}
