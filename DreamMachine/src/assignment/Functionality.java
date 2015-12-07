package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Functionality {
	
	static int user_id; 
	static final String QUIZ_TAKE_PROMPT = "\nDIRECTIONS: Enter the number by a quiz to take it. Type 0 to return to main menu. ";
	static final String PROMPT = "\nDIRECTIONS: Type 1 to take quiz. Type 2 to make quiz. Type 3 to switch users. Type 0 to quit.";
	static final String WELCOME_MESSAGE = "\nWelcome to Dream Creatures Quiz Making Creating Terminal Program of Sadness\n";
	static final String SELECT_USER = "\nSelect user account 1, 2, or 3";
	private static final int NUM_OPTIONS = 3;
	
	static BufferedReader br; 
	
	public static void main (String args []) {
		DBConnection.connect();
		System.out.println(WELCOME_MESSAGE);
		user_id = getUser();
		
		while(true) {
			int action = getAction();
			if (action == 0) break;

			switch(action){
				case 1:
					takeQuiz();
					break;
				case 2:
					createNewQuiz();
					break;
				case 3:
					user_id = getUser();
					break;
			}
		}
		
		System.out.println("Thanks for playing!");
	}
	
	private static int getUser() {
		int user;
		br = new BufferedReader(new InputStreamReader(System.in));

		
		System.out.println(SELECT_USER);
		while (true) {
			System.out.print("Your choice: ");
			try {
				user = Integer.parseInt(br.readLine());
				if (user >= 1 && user <= 3) return user;

			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.println("Please enter a valid integer between 1 and 3");
		}
	}
	
	public static Answer createAnswer(int type, String options){
		switch(type) {
			case Question.RESPONSE: 
				return new Response(options); 
		
			case Question.MULTICHOICE:
				return new MultiChoice(options);

			case Question.MATCHING:
				return new Matching(options);
		}
		return null;
		
	}
	
	private static void listQuizzes(ArrayList<Quiz> quizzes){
		System.out.println("\nAll Quizzes in Database: ");
		for (int i = 1, size = quizzes.size(); i <= size; i++) 
			System.out.println(i+") " + quizzes.get(i-1).name);
		System.out.println();
	}
	
	private static int getAction() {
		int action;
		br = new BufferedReader(new InputStreamReader(System.in));

		
		System.out.println(PROMPT);
		while (true) {
			System.out.print("Your choice: ");  
			try {
				action = Integer.parseInt(br.readLine());
				if (action >= 0 && action <= NUM_OPTIONS) break;

			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.println("Please enter a valid integer between 1 and " + NUM_OPTIONS);
		}
		
		return action; 
	}

	private static Quiz selectQuiz() {
		br = new BufferedReader(new InputStreamReader(System.in));

		ArrayList<Quiz >quizzes = populateQuizzesFromDB();
		if(quizzes.isEmpty()) quizzes.add(makeSampleQuiz());
		listQuizzes(quizzes);
		
		int action;
		System.out.println(QUIZ_TAKE_PROMPT);
		while (true) {
			System.out.print("Your choice: ");  
			try {
				action = Integer.parseInt(br.readLine());
				if (action > 0 && action <= quizzes.size()) return quizzes.get(action-1);

			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
	}
	
	private static int playQuiz(Quiz currQuiz) {
		List<Question> questions_local = currQuiz.questions;	
		int totalCorrect = 0; 
		System.out.println("\nYou chose " + currQuiz.name + ". Let's begin the quiz!");

		for (int i = 0, size = questions_local.size(); i < size; i++) {
			br = new BufferedReader(new InputStreamReader(System.in));

			Question currQuestion = questions_local.get(i); 
			String answer = "";
			int numCorrect = 0; 
			int numAnswers = 0; 

			switch(currQuestion.type) {
			
				case Question.RESPONSE:
					System.out.println("\n"+(i+1) + ") RESPONSE QUESTION: " + currQuestion.question);
					
					numAnswers = ((Response) currQuestion.answer).numAnswers; 
					for (int rindex = 0; rindex < numAnswers; rindex++) {
						System.out.print("Please type response " + (rindex+1) + " of " + numAnswers+ ": ");
						if (rindex > 0) answer += "<>";
						try {
							answer += br.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}	
					}
					break;
					
					
				case Question.MULTICHOICE:
					System.out.println("\n"+(i+1) + ") MULTIPLE CHOICE QUESTION: " + currQuestion.question);
					
					List<String>allOptions = ((MultiChoice) currQuestion.answer).allOptions;
					
					for (int index = 0, max = allOptions.size(); index < max; index++){
						System.out.println((char)('A' + index) + ") " + allOptions.get(index));
					}
					
					numAnswers = ((MultiChoice) currQuestion.answer).allAnswers.size(); 
					for (int mindex = 0; mindex < numAnswers; mindex++) {
						if (mindex != 0) answer += "<>";
						System.out.print("Please letter choice " + (mindex+1) + " of " + numAnswers+ ": ");
						try {
							int index = ((int) br.readLine().toUpperCase().charAt(0) - 'A');
							if (index >= 0 && index < numAnswers) answer += allOptions.get(index);
						} catch (IOException e) {
							e.printStackTrace();
						}	
					}
					break; 	
				default: break; 
			}
			
			numCorrect = currQuestion.checkAnswer(answer);
			System.out.println("Correctly answered " + numCorrect + " of " + numAnswers);
			totalCorrect += numCorrect;
		}
		return totalCorrect;
	}

	private static void takeQuiz() {
		Quiz currQuiz = selectQuiz();
		int score = playQuiz(currQuiz); 
		String insertStr = "INSERT INTO scores (quiz_id, user_id, score) value(" +
							currQuiz.quiz_id +","+ user_id +","+ score + ");";
		DBConnection.update(insertStr);
	}
	
	
	public static Quiz createQuiz(int user_id, String quizName, String description) {
		Quiz quiz = new Quiz();
		quiz.user_id = user_id;
		quiz.name = quizName;
		quiz.description = description; 
		quiz.single_page = true; 
		quiz.random_questions = false;
		quiz.immediate_correct = false;
		quiz.practice_mode = false; 
		quiz.save(); 
		return quiz; 
	}

	private static void createNewQuiz() {
		br = new BufferedReader(new InputStreamReader(System.in));

		String quizName = "";
		String quizDescription = "";
		System.out.println("ENTER QUIZ NAME: ");
		try {
			quizName = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ENTER QUIZ DESCRIPTION: ");
		try {
			quizDescription = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int user_id = 10;
		
		Quiz quiz = createQuiz(user_id, quizName, quizDescription);
		int quiz_id = quiz.quiz_id;
		addQuestions(quiz_id);
	}
	
	
	public static Question createQuestion(int quiz_id, String questionStr, int type, Answer answer ) {
		Question question = new Question(type);
		question.quiz_id = quiz_id;
		question.question = questionStr; 
		question.answer = answer;
		question.imageName = "";
		question.type = type;
		question.save(); 
		return question; 
	}

	private static void addQuestions(int quiz_id) {
		br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			System.out.println("What kind of question would you like to add (Response or Multiple Choice)?, 'quit' to quit");
			System.out.println("Type 'R' or 'MC' (without the ' ')");
			
			String questionType = "";
			try {
				questionType = br.readLine();
				if (questionType.equals("quit"))break;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			int type = 0;
			if (questionType.equals("R")) type = 1;
			if (questionType.equals("MC")) type = 2;
			
			System.out.println("DIRECTION: Please type the question");
			String question = "";
			try {
				question = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String answerString = "";
			Answer answer = new Answer();
			if (type == 1) {
				System.out.println("DIRECTION: Please type the answer to your questions. \nIf this is "
						+ "a multiple part question, split the answers by using this symbol '<>' \n"
						+ "If there are different answers that might all count as correct, separate them by using a comma");
				try {
					answerString = br.readLine();
					answer = new Response(answerString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (type == 2) {
				
				String multipleAnswers = "Patrick Young" + Answer.SEPERATOR + "Mehran" + Answer.SEPERATOR + "Keith" + 
						Answer.SEPERATOR + "Cynthia Bailey" + Answer.DELIM + "Mehran" + Answer.SEPERATOR	 + "Keith";

				
				System.out.println("DIRECTION: Please type the options and the answer on one line. "
						+ "\nSeparate the options using a comma and then separate the options from the answer using this symbol '<>'"
						+ "\nEXAMPLE: 'choiceA,choiceB,choiceC<>choiceA,choiceC'");
				try{
					answerString = br.readLine();
					answer = new MultiChoice(answerString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			Question entireQuestion = createQuestion (quiz_id, question, type, answer);
		}
	}


	public static ArrayList<Quiz> populateQuizzesFromDB() {
		ArrayList<Quiz> allQuizzes = new ArrayList<Quiz>();
		
		String query_selectall = "SELECT * FROM quizzes";
		ResultSet rs = DBConnection.query(query_selectall);
		try {
			while(rs.next()) {
				Quiz newQuiz = new Quiz(rs);
				newQuiz.loadQuestions();
				allQuizzes.add(newQuiz);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return allQuizzes;
	}

	private static Quiz makeSampleQuiz() {
		String quizName = "The CS Classes At Stanford";
		String description = "Test whether you are familiar with CS Classes at Stanford!";
		
		String responseQuestion = "Who teaches CS108?";
		String responseAnswers = "Patrick Young" + Answer.SEPERATOR + "Professor Young" + Answer.SEPERATOR + "Patrick";
		Response responseResponse = new Response(responseAnswers); 

		String responseUserAnswer = "Patrick";
		String responseWrongUserAnswer = "PYoung";
		int responseType = Question.RESPONSE;
	
		Answer RESPONSE_answer = createAnswer(Question.RESPONSE, responseAnswers);
		Quiz quiz = createQuiz(user_id, quizName, description);
		Question RESPONSE_question = createQuestion(quiz.quiz_id, responseQuestion, responseType, RESPONSE_answer);

		String responseMultiAnswerQuestion = "Name the three introductory CS courses";
		String responseMultiAnswerAnswers = "CS106A" + Answer.SEPERATOR + "CS 106A" + Answer.DELIM + "CS106B" + Answer.SEPERATOR + "CS 106B" + 
											Answer.DELIM + "CS106X" + Answer.SEPERATOR + "CS 106X";
		Response responseMultiAnswerResponse = new Response(responseMultiAnswerAnswers); 

		String responseMultiAnswerUserAnswer = "CS106A" + Answer.DELIM + "CS 106B" + Answer.DELIM + "CS106X";
		String responseMultiAnswerWrongUserAnswer = "CS108";
		int responseMultiAnswerType = Question.RESPONSE;
	
		Answer RESPONSEMULTI_answer = createAnswer(Question.RESPONSE, responseMultiAnswerAnswers);
		Question RESPONSEMULTI_question = createQuestion(quiz.quiz_id, responseMultiAnswerQuestion, responseMultiAnswerType, RESPONSEMULTI_answer);

		String multipleQuestion = "Who teaches CS106A?";
		String multipleAnswers = "Patrick Young" + Answer.SEPERATOR + "Mehran" + Answer.SEPERATOR + "Keith" + 
									Answer.SEPERATOR + "Cynthia Bailey" + Answer.DELIM + "Mehran" + Answer.SEPERATOR	 + "Keith";

		String multipleUserAnswer = "Mehran" + Answer.DELIM + "Keith";
		String multipleWrongUserAnswer = "Mehran";
		int multipleType = Question.MULTICHOICE;
		
		Answer MULTIPLE_answer = createAnswer (Question.MULTICHOICE, multipleAnswers);
		Question MULTIPLE_question = createQuestion (quiz.quiz_id, multipleQuestion, multipleType, MULTIPLE_answer);
		
		return quiz; 
	}	
}
