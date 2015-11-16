package assignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.*;

public class Question {
	public enum Type {
	    RESPONSE, MULTICHOICE, MATCHING 
	}
	
	public int id; 
	public Type type; 
	public String question;
	public Answer answer; 

	public String imageName; 
	public boolean containsImage; // why is this necessary? can't you just check whether imageName is null?

	public boolean saved; 

	public static final String DELIM = "<>"; 
	
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
	
	public Question (ResultSet questionData) {
		try {
			this.id = questionData.getInt("pid");
			this.question = questionData.getString("question");
		
			String imageName = questionData.getString("imageName");
			this.setContainsImage(imageName.isEmpty() ? false : true);
			this.imageName = imageName;
		
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
	
	public boolean isValid() {
		if (!answer.isValid()) return false; 
		if (getType() == null || getQuestion() == null ||
			(containsImage() && getImageName() == null)) {
			return false;
		}
		return true;
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
		//int qid = getQID();
		String entry = "INSERT INTO questions (qid, type, question, answer, imageName) " +
				"VALUES("+ id +","+ type +","+ question +","+ answer.toString() +","+ imageName +");";
		DBConnection.insertUpdate(entry);
		return true;
	}
	
	public String toString(){
		return question; 
	}
	
	public ArrayList<String> arrayListFromString (String ansStr) {
		int start = ansStr.indexOf('[') + 1,
			end = ansStr.lastIndexOf(']');
		String data = ansStr.substring(start, end); 
		return new ArrayList<String>(Arrays.asList(data.split(", ")));			
	}
	
	public void setQuestion(String question) {
		this.question = question;
	} 
	
	public String getQuestion() {
		return question;
	}
	
	public Type getType() {
		return type; 
	}
	
	public int getID() {
		return id; 
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageStr) {
		setContainsImage(imageStr.isEmpty() ? false : true); 
		imageName = imageStr.isEmpty() ? null : imageStr; 
	}

	public boolean containsImage() {
		return containsImage;
	}

	public void setContainsImage(boolean containsImage) {
		this.containsImage = containsImage;
	}
	
	public Answer getAnswer() {
		return answer; 
	}
}
