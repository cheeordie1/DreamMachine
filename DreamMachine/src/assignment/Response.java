package assignment;

import java.util.ArrayList;
import java.util.Arrays;

public class Response extends Answer {
	
	private ArrayList <ArrayList<String>> answers;
	private int numAnswers; 
	private final int numElem = 2; 

	public Response(String options) {
		setAnswersFromString(options); 
		this.numAnswers = answers.size(); 
	}
	
	public Response() {
		// TODO Auto-generated constructor stub
	}

	private void setAnswersFromString(String options) {
		answers = new ArrayList <ArrayList<String>>(); 
		int 	start = options.indexOf('[') + 1,
				end = options.lastIndexOf(']');
		String data = options.substring(start, end); 
		
		
	
		// TODO Auto-generated method stub
	}

	public boolean correctAnswer(String answer){
		return answers.contains(answer);
	}
	
	
	public static void main (String[] args) {
		ArrayList<String> names = new ArrayList<String>();
		names.add("sage");
		names.add("mark");
		names.add("betsy");
		String questionString = "Who are you?" + Question.DELIM + names;
	}
	
	
	
	
	
	

}
