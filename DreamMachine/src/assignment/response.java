package assignment;

import java.util.ArrayList;

public class response extends Question {
	
	ArrayList <String> answers;
	String question; 
	String selectedAnswer; 
	

	public response(Type type) {
		super(Question.Type.RESPONSE);
	}

	public response(Type type, String questionString) {
		super(Question.Type.RESPONSE, questionString);
	}
	
	boolean correctAnswer(String answer){
		return answers.contains(answer);
	}
	
	
	
	

}
