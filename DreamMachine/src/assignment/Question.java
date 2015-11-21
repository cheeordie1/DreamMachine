package assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;

public class Question {
	
	/* main categories of questions */
	public enum Type {
	    RESPONSE, MULTICHOICE, MATCHING 
	}
	
	public int id;
	public int q_id; 
	public Type type; 
	public String question;
	public Answer answer; 
	public String imageName;  
	
	/* constructor for new question */
	public Question (Type type) {
		this.type = type; 
		
		switch(type) {
			case RESPONSE: 
				answer = new Response(); 
				break;
		
			case MULTICHOICE: 
				answer = new MultiChoice(); 
				break;
		
			case MATCHING: 
				answer = new Matching(); 
				break; 
		}
	}
	
	/* constructor for question from database */
	public Question (ResultSet questionData) {
		try {
			this.id = questionData.getInt("pid");
			this.q_id = questionData.getInt("qid");
			this.question = questionData.getString("question");
			this.imageName = questionData.getString("imageName");
		
			String options = questionData.getString("options");
			switch(type) {
				case RESPONSE: 
					answer = new Response(options); 
					break;
	
				case MULTICHOICE:
					answer = new MultiChoice(options);
					break;
		
				case MATCHING:
					answer = new Matching(options);
					break;
			}
		} catch (Exception ex) {
			
		}
	}
	
	/* ensures that the question is properly formatted to be saved */
	public boolean isValid() {
		return type != null && question != null && answer.isValid();
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
		String entry = "INSERT INTO questions (qid, qtype, question, answer, imageName) " +
				"VALUES("+ q_id +","+ type +","+ question +","+ answer.toString() +","+ imageName +");";
		id = DBConnection.update(entry);
		return true;
	}
	
	public String toString(){
		return "id: " + id + "Type: " + type + "Question: " + question + "Image Name: " + imageName + "Answer: " + answer; 
	}

}
