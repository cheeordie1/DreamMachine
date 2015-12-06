package assignment;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Response extends Answer {
	// instance variables
	public boolean ordered;
	public String subset;
	public int subsetNum;
	private ArrayList <ArrayList<String>> answerOptions;
	public int numAnswers; 
	
	// static variables
	public static final String ORDERED = "*";
	
	// error types
	public static final String ANSWER_ERROR = "answer";
	public static final String SUBSET_ERROR = "subset";
	
	// error strings
	public static final String ANSWER_EMPTY = "Please fill in at least 1 answer slot.";
	public static final String SUBSET_INVALID = "Please enter a correct number";
	
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

	/**
	 * Override the save function in Answer to allow for response-specific
	 * saving.
	 * @return true if save works, false otherwise
	 */
	@Override
	public boolean save() {
		boolean error = false;
		if (subsetEmpty() || subsetInvalid()) error = true;
		if (answersEmpty()) error = true;
		if (error) return false;
		answer = this.toString();
		return super.save();
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
		if (ordered) options += '*';
		if (subset != null) options += "<" + subset + ">";
		for  (int listIndex = 0, listIndexLen = answerOptions.size(); listIndex < listIndexLen; listIndex++){
			if (listIndex != 0) options += DELIM;
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
	 * 1 answer, multiple forms: "*<1>Obama=Barrack Obama=Barrack"
	 * 2 answers, multiple forms: "obama=Obama,St.Nick=Santa Claus=Santa"
	 */
	public void parseOptions (String options) {
		if (options.charAt(0) == '*') {
			options = options.substring(1);
			ordered = true;
		}
		int start = options.indexOf('<');
		int end = options.indexOf('>');
		subset = options.substring(start + 1, end);
	    subsetNum = Integer.parseInt(subset);
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
		if (answer == null) return;
		if (answer.matches("\\s*")) return;
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
	
	/**
	 * Validate subset is filled
	 * @return true if subset is null, false otherwise
	 */
	public boolean subsetEmpty() {
		if (subset == null) {
			errorMessages.addError(SUBSET_ERROR, SUBSET_INVALID);
			return true;
		}
		return false;
	}
	
	/**
	 * Validate subset number
	 * @return true if subset unparseable, false otherwise
	 */
	public boolean subsetInvalid() {
		try {
			Integer.parseInt(subset);
		} catch (NumberFormatException nfe) {
			errorMessages.addError(SUBSET_ERROR, SUBSET_INVALID);
			return true;
		}
		return false;
	}
	
	/**
	 * Validate that at least one answer was given
	 * @return true if answers are empty, false otherwise
	 */
	public boolean answersEmpty() {
		if (answerOptions.isEmpty()) {
			errorMessages.addError(ANSWER_ERROR, ANSWER_EMPTY);
		}
		return false;
	}
}
