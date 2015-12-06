package assignment;

import java.sql.*;

public class Question {
	
	
	public static final int RESPONSE = 1;
	public static final int MULTICHOICE = 2;
	public static final int MATCHING = 3;

	public int question_id;
	public int quiz_id; 
	public int type; 
	public String question;
	public Answer answer; 
	public String imageName;  
	public int typeInt;
	
	public Question (int type) {
		this.type = type; 
		
		switch(type) {
			case RESPONSE: 
				typeInt = 0;
				answer = new Response(); 
				break;
		
			case MULTICHOICE: 
				typeInt = 1;
				answer = new MultiChoice(); 
				break;
		
			case MATCHING: 
				typeInt = 2;
				answer = new Matching(); 
				break; 
		}
	}
	
	public Question (ResultSet questionData) {
		try {
			this.question_id = questionData.getInt("question_id");
			this.quiz_id = questionData.getInt("quiz_id");
			this.question = questionData.getString("question");
			this.type = questionData.getInt("question_type");
			String options = questionData.getString("answer_id");
			switch(type) {
				case RESPONSE: 
					typeInt = 0;
					answer = new Response(options); 
					break;
	
				case MULTICHOICE:
					typeInt = 1;
					answer = new MultiChoice(options);
					break;
		
				case MATCHING:
					typeInt = 2;
					answer = new Matching(options);
					break;
			}
		} catch (Exception ex) {
			
		}
	}
	
	public boolean isValid() {
		return (type == RESPONSE ||  type == MULTICHOICE || type == MATCHING)  && question != null && imageName != null && answer.isValid();
	}
	
	public boolean checkAnswer(String userInput){
		switch(type) {
		case RESPONSE: 
			return ((Response) answer).checkAnswer(userInput); 

		case MULTICHOICE:
			return ((MultiChoice) answer).checkAnswer(userInput); 

		case MATCHING:
			return ((Matching) answer).checkAnswer(userInput);
		}
		return false;
	}
	
	
	/*
	 * Validates that the question is valid.
	 * If the question is already in the database then it 
	 * replaces the entry. Otherwise it adds a new quiz
	 * to the DBConnection.Question Table. 
	 */
	public boolean save() {
		if (!isValid()) {
			return false;
		}
		String entry = "INSERT INTO questions (quiz_id, question_type, question, answer) " +
				"VALUE("+ quiz_id +","+ type +",'"+ question +"','"+ answer.toString() +"')";
		question_id = DBConnection.update(entry);
		return true;
	}
	
	public String toString(){
		return "id: " + question_id + "Type: " + type + "Question: " + question + "Image Name: " + imageName + "Answer: " + answer; 
	}

}