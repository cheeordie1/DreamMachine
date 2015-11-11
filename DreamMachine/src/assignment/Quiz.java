package assignment;

import java.util.Collections;
import java.util.List;
import java.util.Date;


public class Quiz {
	
	public int idNumber;
	public Date dateCreated;
	public int numTimesPlayed;
	
	public List <Questions> allQuestions;
	public List <HighestScore> highestScores;
	
	
	boolean singlePage;
	boolean randQuestion;
	boolean immediateCorrect;
	boolean practiceMode;
	
	public Quiz (boolean singlePage, boolean randQuestion, boolean immediateCorrect, boolean practiceMode) {
		this.singlePage = singlePage;
		this.randQuestion = randQuestion;
		this.immediateCorrect = immediateCorrect;
		this.practiceMode = practiceMode;
		
		/*
		 * What is the best way to come up with unique ID numbers/values.
		 * Set that new unique ID num/value equal to idNumber!
		 */
		
		numTimesPlayed = 0;
		dateCreated = new Date();
		
		/*
		 * If randQuestion is true, that means that the quiz should have
		 * a random order of questions. 
		 */
		if (randQuestion) {
			Collections.shuffle(allQuestions);
		}
		
		
	}
	
	public void addQuestion (Question q) {
		allQuestions.add(q);
	}
	
	
	public void quizPlayed () {
		numTimesPlayed++;
	}
	
	public void enterScore (int score, int time) {
		HighestScore newScore = new HighestScore (score, time);
		highestScores.add(newScore);
	}
	
	public class HighestScore {
		int score;
		int time;
		
		public HighestScore (int score, int time) {
			this.score = score;
			this.time = time;
		}
	}
	
	
}
