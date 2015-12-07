package assignment;

import java.util.ArrayList;
import java.util.Arrays;

public class Response extends Answer {
	
	private ArrayList <ArrayList<String>> answerOptions;
	public int numAnswers; 
	
	public Response() {
		answerOptions = new ArrayList <ArrayList<String>>();
		numAnswers = 0;
	}
	
	public Response(String options) {
		answerOptions = new ArrayList <ArrayList<String>>();
		parseOptions(options); 
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
				if (i != 0) options += SEPERATOR;
				options += answerList.get(i); 
			}	 
		}
		return options; 
	}
	
	/*
	 * Function that parses the string object of the multiple 
	 * choice question. Then it stores all the options into an
	 * internal options list and the answers into an internal
	 * answers list.
	 */
	public void parseOptions (String options) {
		//example of options string => "Obama SEPERATOR Barrack SEPERATOR Barrack Obama DELIM The White House SEPERATOR White House"
		
		String [] parsedAnswerLists = options.split(DELIM);
		
		for (int i = 0, len = parsedAnswerLists.length; i < len; i++) {
			String parsedAnswerListStr = parsedAnswerLists[i]; 
			String [] parsedAnswerArray = parsedAnswerListStr.split(SEPERATOR);
			answerOptions.add(new ArrayList<String>(Arrays.asList(parsedAnswerArray)));
		}
	}
	
	/*
	 * Boolean that checks the user's inputted answer and returns
	 * whether it is a valid answer or not.
	 */
	@Override
	public boolean checkAnswer (String userInput) {
		//user input has to have "<>"
		//example of user input: "milk<>sausage"
		
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
		for (boolean isCorrect : correctResponses) if (!isCorrect) return false; 
		return true;
	}
	
	/*
	 * Function that takes in a string of valid answer options for
	 * and answer and adds it into the answer options list
	 */
	public void addAnswer (String answer) {
		String [] parsedAnswerArray = answer.split(SEPERATOR);
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

	/*
	 * Function that validates a response question.
	 * First it checks that the size of answerOptions and options do not equal zero.
	 */
	@Override
	public boolean isValid () {
		if (answerOptions.size() == 0 || numAnswers == 0) return false;
		for (ArrayList<String> answerList: answerOptions) {
			if (answerList.isEmpty()) return false;
		}
		return true;
	}
	
//	public static void main (String[] args) {
//		Response response1 = new Response(); 
//		response1.addAnswer("Sage, Mary, Bob");
//		
//		System.out.println(response1.isValid());
//		System.out.println(response1.checkAnswer("Sage"));
//		
//		response1.addAnswer("lime, orange, lemon");
//		
//		System.out.println(response1.isValid());
//		System.out.println(response1.checkAnswer("lemon" + DELIM + "Bob"));
//		
//		response1.deleteFromAnswer("lemon", 1);
//		System.out.println(response1.isValid());
//		System.out.println(response1.checkAnswer("lemon"));
//		
//	}
}