package assignment;

import java.util.*;

public class MultiChoice extends Answer {

	private List <String> allAnswers;
	private List <String> allOptions;
	private int numAnswers;
	
	/*
	 * Constructor for the multiple choice questions when
	 * we are grabbing a pre-existing question from the 
	 * database.
	 */
	public MultiChoice (String options) {
		allAnswers = new ArrayList <String> ();
		allOptions = new ArrayList <String> ();
		numAnswers = 0;
		parseOptions(options);
	}
	
	/*
	 * Constructor for the multiple choice questions when
	 * user is creating a fresh new MC question.
	 */
	public MultiChoice () {
		allAnswers = new ArrayList <String> ();
		allOptions = new ArrayList <String> ();
		numAnswers = 0;
	}
	
	/*
	 * Function that takes in a string that is an answer
	 * and adds it into the all answer list.
	 */
	public void addAnswer (String answer) {
		allAnswers.add(answer);
		numAnswers++;
	}
	
	/*
	 * Function that takes in a string that is an option
	 * and adds it into the option list.
	 */
	public void addOption (String option) {
		allOptions.add(option);
	}
	
	/*
	 * Function that takes in the option that the user
	 * wants to delete and removes it from the list of 
	 * options. It also checks if the option was a selected 
	 * answer. If so, it removes it from the answers list
	 * as well.
	 */
	public void deleteOption (String option) {
		allOptions.remove(option);
		if (allAnswers.contains(option)) {
			allAnswers.remove(option);
			numAnswers--;
		}
	}
	
	/*
	 * Function that takes in a string answer and removes it 
	 * from the list of answers. 
	 */
	public void deleteAnswer (String answer) {
		allAnswers.remove(answer);
	}

	/*
	 * Function that validates a multiple choice question.
	 * First it checks that the size of answers and options do not equal zero.
	 * Second it checks that each answer is a valid option.
	 */
	
	@Override
	public boolean isValid() {
		if (allAnswers.size() == 0 || allOptions.size() == 0 || numAnswers == 0) return false;
		for (String a: allAnswers) {
			if (!allOptions.contains(a)) return false;
		}
		return true;
	}
	
	/*
	 * Function that returns a string object version of the 
	 * multiple choice question. It returns a string that has
	 * the options, separated by commas and then the answers, 
	 * also separated by commas. Between the options and answers
	 * there is a deliminator.
	 */
	
	@Override
	public String toString () {
		String result = "";
		for (int i = 0; i < allOptions.size(); i++) {
			if (i != allOptions.size() -1 ) {
				result += allOptions.get(i) + SEPERATOR;
			} else {
				result += allOptions.get(i);
			}
		}
		
		result += DELIM;
		
		for (int i = 0; i < allAnswers.size(); i++) {
			if (i != allAnswers.size() - 1) {
				result += allAnswers.get(i) + SEPERATOR;
			} else {
				result += allAnswers.get(i);
			}
		}
		return result;
	}
	
	
	/*
	 * Function that parses the string object of the multiple 
	 * choice question. Then it stores all the options into an
	 * internal options list and the answers into an internal
	 * answers list.
	 */
	public void parseOptions (String options) {
		//example of options string => "sun, fly, sage<>sun"
		
		String [] parsedAnswers = options.split(DELIM);
		
		String optionsToSplit = parsedAnswers[0];
		String answersToSplit = parsedAnswers[1];
		
		String [] splitOptions = optionsToSplit.split(SEPERATOR);
		for (String option: splitOptions) allOptions.add(option);
		
		String [] splitAnswers = answersToSplit.split(SEPERATOR);	
		for (String answer: splitAnswers) allAnswers.add(answer);
		
		numAnswers = splitAnswers.length;
	}
	
	/*
	 * Boolean that checks the user's inputted answer and returns
	 * whether it is a valid answer or not.
	 */
	public boolean checkAnswer (String userInput) {
		//user input has to have "<>"
		//example of user input: "milk<>sausage"
		
		String [] splitUserInput = userInput.split(DELIM);
		for (String answer: splitUserInput) {
			if (!allAnswers.contains(answer)) return false;
		}
		
		if (allAnswers.size() != splitUserInput.length) return false;
		
		return true;
	}
	
	public void printContents(){
		System.out.println("Options:");
		for (String s: allOptions) System.out.println(s);
		System.out.println(" ");
		System.out.println("Answers: ");
		for (String s: allAnswers) System.out.println(s);
		System.out.println(" ");
		System.out.println("Number of answers: " + numAnswers);
		System.out.println(" ");
	}

}