package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Functionality {
	
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
	
	public static Question createQuestion(int quiz_id, String questionStr, Question.Type type, Answer answer ) {
		Question question = new Question(type);
		question.quiz_id = quiz_id;
		question.question = questionStr; 
		question.answer = answer;
		question.imageName = "";
		question.save(); 
		return question; 
	}
	
	public static Answer createAnswer(Question.Type type, String options){
		switch(type) {
			case RESPONSE: 
				return new Response(options); 
		
			case MULTICHOICE:
				return new MultiChoice(options);

			case MATCHING:
				return new Matching(options);
		}
		return null;
		
	}
	
	private static void listQuizzes(){
		System.out.println("All Quizzes in Database: ");
		for (int i = 1, size = quizzes.size(); i <= size; i++) 
			System.out.println(i+") " + quizzes.get(i-1).name);
		System.out.println();

	}
	
	private static int getAction() {
		int action = 0;
		
		System.out.println(PROMPT + "Or type " + (quizzes.size()+1) + " to quit!");
		while (true) {
			System.out.print("Your choice: ");  
			action = Integer.parseInt(scanner.next()); 
			scanner.reset();
			if (action >= 0 && action <= quizzes.size()+1) break;
			else System.out.println("Please enter a valid integer between 0 and " + quizzes.size() + "!");
		}
		
		return action; 
	}
	
	static Scanner scanner;
	static final int user_id = 1; 
	static final String PROMPT = "Enter the number by a quiz to take it. Type 0 to make a quiz! ";
	static final String WELCOME_MESSAGE = "Welcome to Dream Creatures Quiz Making Creating Terminal Program of Sadness\n";
	private static ArrayList<Quiz> quizzes;
	
	public static void main (String args []) {
		DBConnection.connect();
		quizzes = populateQuizzesFromDB();
		quizzes.add(makeSampleQuiz());
		scanner = new Scanner(System.in);
		
		System.out.println(WELCOME_MESSAGE);
		
		while(true) {
			//Go through database and populate quizzes
			listQuizzes();
			
			//Figure out next steps
			int action = getAction(); 
			//Quit Program
			if (action == quizzes.size()+1) break; 
		
			switch(action){
				case 0:
					createNewQuiz();
					break;
				default: 
					takeQuiz(action-1); 
					break; 
			}
		}
		
		System.out.println("Thanks for playing!");
		scanner.close();
	}

	private static void takeQuiz(int action) {
		// TODO Auto-generated method stub
		
	}

	private static void createNewQuiz() {
		
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//	    System.out.print("Enter String");
//	    try {
//			String s = br.readLine();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
		System.out.print("Enter Quiz Name: "); 
		String name = scanner.nextLine(); 
		
		System.out.print("Enter Quiz Description: ");  
		String description = scanner.next(); 
		
		Quiz quiz = createQuiz(user_id, name, description);
		
		addQuestions();
		
		//scanner.close();
	}

	private static void addQuestions() {
			
			//Print out asking what type of questions or finish making quiz
			//
		
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
		Question.Type responseType = Question.Type.RESPONSE;
	
		Answer RESPONSE_answer = createAnswer(Question.Type.RESPONSE, responseAnswers);
		Quiz quiz = createQuiz(user_id, quizName, description);
		Question RESPONSE_question = createQuestion(quiz.quiz_id, responseQuestion, responseType, RESPONSE_answer);

		String responseMultiAnswerQuestion = "Name the three introductory CS courses";
		String responseMultiAnswerAnswers = "CS106A" + Answer.SEPERATOR + "CS 106A" + Answer.DELIM + "CS106B" + Answer.SEPERATOR + "CS 106B" + 
											Answer.DELIM + "CS106X" + Answer.SEPERATOR + "CS 106X";
		Response responseMultiAnswerResponse = new Response(responseMultiAnswerAnswers); 

		String responseMultiAnswerUserAnswer = "CS106A" + Answer.DELIM + "CS 106B" + Answer.DELIM + "CS106X";
		String responseMultiAnswerWrongUserAnswer = "CS108";
		Question.Type responseMultiAnswerType = Question.Type.RESPONSE;
	
		Answer RESPONSEMULTI_answer = createAnswer(Question.Type.RESPONSE, responseMultiAnswerAnswers);
		Question RESPONSEMULTI_question = createQuestion(quiz.quiz_id, responseMultiAnswerQuestion, responseMultiAnswerType, RESPONSEMULTI_answer);

		String multipleQuestion = "Who teaches CS106A?";
		String multipleAnswers = "Patrick Young" + Answer.SEPERATOR + "Mehran" + Answer.SEPERATOR + "Keith" + 
									Answer.SEPERATOR + "Cynthia Bailey" + Answer.DELIM + "Mehran" + Answer.SEPERATOR	 + "Keith";

		String multipleUserAnswer = "Mehran" + Answer.DELIM + "Keith";
		String multipleWrongUserAnswer = "Mehran";
		Question.Type multipleType = Question.Type.MULTICHOICE;
		
		Answer MULTIPLE_answer = createAnswer (Question.Type.MULTICHOICE, multipleAnswers);
		Question MULTIPLE_question = createQuestion (quiz.quiz_id, multipleQuestion, multipleType, MULTIPLE_answer);
		
		return quiz; 
	}	
}
