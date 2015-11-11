package assignment;

import java.util.ArrayList;

public class Response extends Question {
	
	ArrayList <String> answers;
	String question; 
	String selectedAnswer; 
	

	public Response(Type type) {
		super(Question.Type.RESPONSE);
	}

	public Response(Type type, String questionString) {
		super(Question.Type.RESPONSE, questionString);
	}
	
	public boolean correctAnswer(String answer){
		return answers.contains(answer);
	}
	
	public String toString(){
		return question + "}{" + answers; 
	}
	
	
	
	

}
