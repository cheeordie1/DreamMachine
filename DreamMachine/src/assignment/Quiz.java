package assignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;


public class Quiz {
	//IDs that do not change
	private int q_id;
	private int u_id; 
	private Date date;
	
	private ArrayList <Question> questions;
	
	//Quiz Options with Default Values Set
	private boolean singlePage = true;
	private boolean randQuestion = false;
	private boolean immediateCorrect = false;
	private boolean practiceModeOption = false;

	/*
	 * Constructor for making a quiz on website
	 */
	public Quiz (int u_id, Date date) {
		this.u_id = u_id; 
		this.date = date; 
	}
	
	/*
	 * Constructor for uploading quiz from database 
	 */
	public Quiz (int q_id, int u_id, Date date, boolean singlePage, boolean randQuestion, boolean practiceModeOption) {
		this.setQ_id(q_id); 
		this.u_id = u_id; 
		this.date = date;  
		this.singlePage = singlePage; 
		this.randQuestion = randQuestion; 
		this.practiceModeOption = practiceModeOption; 
		populateQuestions(); 
	}
	
	
	/*
	 * Creates a query into the Questions table and 
	 * searches for questions with the same id as the 
	 * quiz. Creates new instances of each question and
	 * adds them to the questions arrayList.  
	 */
	private void populateQuestions() {
		// TODO Mark creates from database
		
	}

	/*
	 * Validates that the questions are all valid.
	 * If the table is already in the database then it 
	 * replaces the entry. Otherwise it adds a new quiz
	 * to the DBConnection.Quiz Table. 
	 */
	public boolean save() {
// 		Should be able to replace pre-existing quiz in the table if edited 
//		if (this.singlePage ||
//		this.randQuestion = randQuestion || 
//		this.immediateCorrect = immediateCorrect || 
//		this.practiceMode = practiceMode) 
//			return false; 
//		
//		String entry =
//                "INSERT INTO " + DBInfo.TABLE + "(username,salt,digest) VALUES (" +
//                    "\"" + this.username + "\"," +
//                    "\"" + this.salt + "\"," +
//                    "\"" + this.passwordDigest + "\"" +
//                ")";
//		
//		this.q_id = DBConnection.insertUpdate(entry);
		return true; 
	}

	/*
	 * Adds question to the questions arrayList
	 */
	public void addQuestion (Question question) {
		questions.add(question);
	}
	
	/*
	 * Adds the users score to the Score table 
	 */
	public void enterScore (int score, int time) {
		//Set into scores database
	}
	
	/*
	 * Queries Score table using q_id and returns
	 * the highest score
	 */
	public int HighScore() {
		//get from database 
		return 0;
	}
	
	/*
	 * Queries Score table using q_id and returns
	 * the length of the results set. 
	 */
	public int timesPlayed() {
		//get from database 
		return 0;
	}
	
	public boolean isRandQuestion() {
		return randQuestion;
	}


	public void setRandQuestion(boolean randQuestion) {
		this.randQuestion = randQuestion;

		if (randQuestion) {
			Collections.shuffle(questions);
		}
		
	}

	public boolean isImmediateCorrect() {
		return immediateCorrect;
	}


	public void setImmediateCorrect(boolean immediateCorrect) {
		this.immediateCorrect = immediateCorrect;
	}

	public boolean isPracticeMode() {
		return practiceModeOption;
	}


	public void setPracticeMode(boolean practiceMode) {
		this.practiceModeOption = practiceMode;
	}
	
	public boolean isSinglePage() {
		return singlePage;
	}


	public void setSinglePage(boolean singlePage) {
		this.singlePage = singlePage;
	}

	public int getQ_id() {
		return q_id;
	}

	public void setQ_id(int q_id) {
		this.q_id = q_id;
	}

	public int getU_id() {
		return u_id;
	}

	public Date getDate() {
		return date;
	}

}
