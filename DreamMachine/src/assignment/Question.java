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
	private String imageName; 
	private boolean containsImage;
	private Answer answer; 

	public static final String DELIM = "<>"; 
	
	public Question (Type type, String questStr, String options) {
		this.id = generateID(); 
		this.type = type; 
		this.question = questStr; 
		
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
	}
	
	public Question (Type type, String question, String imageName, String options) {
		this(type, question, options);
		this.setContainsImage(imageName.isEmpty() ? false : true); 
		this.imageName = imageName.isEmpty() ? null : imageName; 
	}
	
	private int generateID() {
		// TODO Auto-generated method stub
		return 0;
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
