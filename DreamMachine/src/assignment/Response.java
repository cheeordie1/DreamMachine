package assignment;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Response extends Answer {
	// instance variables
	public boolean ordered;
	private ArrayList <ArrayList<String>> answerOptions;
	public int numAnswers; 
	
	// static variables
	public static String ORDERED = "*";
	
	public Response() {
		super();
		answerOptions = new ArrayList <ArrayList<String>>();
		numAnswers = 0;
	}
	
	public Response(ResultSet rs) {
		super(rs);
		answerOptions = new ArrayList <ArrayList<String>>();
		parseOptions(answer); 
		numAnswers = answerOptions.size();
	}

	/*
	 * Function that returns a string object version of the 
	 * response question. It returns a string that has
	 * the correct SEPERATOR separated answer options for a particular field 
	 * separated by DELIM. 
	 */
	@Override
	public String toString () {
		String options = "";
		for  (int listIndex = 0, listIndexLen = answerOptions.size(); listIndex < listIndexLen; listIndex++){
			if (listIndex != 0) options += " " + DELIM;
			ArrayList<String> answerList = answerOptions.get(listIndex);
			for (int i = 0, len = answerList.size(); i < len; i++) {
				if (i != 0) options += SEPARATOR;
				options += answerList.get(i); 
			}	 
		}
		return options; 
	}
	
	/**
	 * Function that parses the string answer to a response
	 * question. Because this can become a multiple response
	 * question, The String form is as follows.
	 * 1 answer, multiple forms: "Obama=Barrack Obama=Barrack"
	 * 2 answers, multiple forms: "obama=Obama,St.Nick=Santa Claus=Santa"
	 */
	public void parseOptions (String options) {
		if (options.charAt(0) == '*') {
			options = options.substring(1);
			ordered = true;
		}
		String[] answers = options.split(SEPARATOR);
		for (String singleAnswer : answers) {
			ArrayList<String> singleAnswerOptions = new ArrayList<String>();
			singleAnswerOptions.addAll(Arrays.asList(singleAnswer.split(DELIM)));
			answerOptions.add(singleAnswerOptions);
		}
	}
	
	/**
	 * Boolean that checks the user's input answer and returns
	 * whether it is a valid answer or not.
	 */
	public boolean checkAnswer (String userInput) {
		// example input: Barack,Santa,Apollo Creed
		boolean correctResponses[] = new boolean[numAnswers];
		Arrays.fill(correctResponses, false);
		
		String [] splitUserInput = userInput.split(DELIM);
		for (String userAnswer: splitUserInput) {
			for (int i = 0, len = answerOptions.size(); i < len; i++) {
				if (correctResponses[i] == true) continue;
				ArrayList<String> answerList = answerOptions.get(i); 
				if (answerList.contains(userAnswer)) {
					correctResponses[i] = true;
					break;
				}
			}
		}
		for (boolean isCorrect : correctResponses) 
			if (!isCorrect) return false; 
		return true;
	}
	
	/*
	 * Function that takes in a string of valid answer options for
	 * and answer and adds it into the answer options list
	 */
	public void addAnswer (String answer) {
		String [] parsedAnswerArray = answer.split(SEPARATOR);
		answerOptions.add(new ArrayList<String>(Arrays.asList(parsedAnswerArray)));
		numAnswers++;
	}
	
	/*
	 * Function that takes in a string that is a valid option and the 
	 * index of the answer it belongs to. Adds it as an option to the
	 * appropriate answers option list. 
	 */
	public boolean addToAnswer (String answer, int index) {
		if (answerOptions.size() < index || index < 0) return false; 
		return answerOptions.get(index).add(answer);
	}
	
	/*
	 * Function that takes in the index of the answer options 
	 * that the user want to delete and removes it from the list of 
	 * answer options.
	 */
	public boolean deleteAnswer (int index) {
		if (answerOptions.size() < index || index < 0) return false; 
		answerOptions.remove(index);
		numAnswers--;
		return true; 
	}
	
	/*
	 * Function that takes in the index of the answer options and the option 
	 * that user wants removed. Removes it as an option to the
	 * appropriate answers option list. 
	 */
	public boolean deleteFromAnswer (String answer, int index) {
		if (answerOptions.size() < index || index < 0) return false; 
		if (!answerOptions.get(index).remove(answer)) return false;
		if (answerOptions.get(index).isEmpty()) deleteAnswer(index);
		return true;
	}	
}
