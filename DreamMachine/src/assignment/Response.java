package assignment;

import java.util.ArrayList;
import java.util.Arrays;

public class Response extends Question {
	
	ArrayList <String> answers;
	String question; 
	String selectedAnswer; 
	private final int numElem = 2; 
	

	public Response(Type type) {
		super(Question.Type.RESPONSE);
	}

	public Response(Type type, String questionString) {
		super(Question.Type.RESPONSE, questionString);
		stringToQuestion(questionString);
	}
	
	public boolean correctAnswer(String answer){
		return answers.contains(answer);
	}
	
	public String toString(){
		return question + Question.DELIM + answers; 
	}
	
	private void stringToQuestion(String questionString){
		//Split the question into all of the separate elements
		String[] split = questionString.split(Question.DELIM); 
		
		question = ""; 
		answers = new ArrayList <String>(); 
		
		if (split.length == numElem) {
			question = split[0]; 
			
			int 	start = split[1].indexOf('[') + 1,
					end = split[1].lastIndexOf(']');
			String data = split[1].substring(start, end); 
			answers = new ArrayList<String>(Arrays.asList(data.split(", ")));			
		}
	}
	
	public static void main (String[] args) {
		ArrayList<String> names = new ArrayList<String>();
		names.add("sage");
		names.add("mark");
		names.add("betsy");
		String questionString = "Who are you?" + Question.DELIM + names;
		Response resp = new Response(Question.Type.RESPONSE, questionString); 
		System.out.println(resp.toString());
	}
	
	
	
	
	
	

}
