package assignment;

public class Question {
	public enum Type {
	    RESPONSE, BLANK, CHOICE, PICTURE, MATCHING 
	}
	
	public static final String DELIM = "<>"; 
	
	private Type type; 
	
	public Question (Type type) {
		this.type = type; 
	}
	
	public Question (Type type, String questionString) {
		this.type = type; 
		System.out.println("undefined String Constructor");
	}
	
	public Type getType() {
		return type; 
	}
	
	public String toString() {
		return "undefined toString Method";
	}

}
