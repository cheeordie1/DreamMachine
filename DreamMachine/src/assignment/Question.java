package assignment;

import java.util.ArrayList;
import java.util.Arrays;

public class Question {
	public enum Type {
	    RESPONSE, MULTICHOICE, MATCHING 
	}
	
	int id; 
	private Type type; 
	private String question;
	private Answer answer; 

	private String imageName; 
	private boolean containsImage;

	private boolean saved; 

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
	
	public Question (Type type, String question, String imageName, String options) {
		this.type = type; 
		this.question = question; 
		
		this.setContainsImage(false); 
		this.imageName = null; 
		
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
		
		this.setContainsImage(imageName.isEmpty() ? false : true); 
		this.imageName = imageName.isEmpty() ? null : imageName; 
	}
	
	public boolean isValid() {
		if (!answer.isValid()) return false; 
		//Check that question exists
		//Are image parameters valid? 
		
		return true; 
	}
	
	
	/*
	 * Validates that the question is valid.
	 * If the question is already in the database then it 
	 * replaces the entry. Otherwise it adds a new quiz
	 * to the DBConnection.Question Table. 
	 */
	public boolean save() {
		/*
		 * Add MARK DOES THISS
		 */
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
