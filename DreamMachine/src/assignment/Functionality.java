package assignment;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Functionality {
	
	static Scanner scanner;
	static final int user_id = 1; 
	static final String PROMPT = "DIRECTIONS: Enter the number by a quiz to take it. Type 0 to make a quiz! ";
	static final String WELCOME_MESSAGE = "Welcome to Dream Creatures Quiz Making Creating Terminal Program of Sadness\n";
	private static ArrayList<Quiz> quizzes;
	
	public static void main (String args []) {
		DBConnection.connect();
		quizzes = populateQuizzesFromDB();
		quizzes.add(makeSampleQuiz());
		quizzes = populateQuizzesFromDB();
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
//					break;
				default: 
					takeQuiz(action-1); 
//					break; 
			}
		}
		
		System.out.println("Thanks for playing!");
		scanner.close();
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


	
	private static void takeQuiz(int action) {
		// TODO Auto-generated method stub
		Quiz currQuiz = quizzes.get(action);
		List<Question> questions_local = currQuiz.questions;
		int i = 0;
		System.out.println("");
		System.out.println("Let's begin the quiz!");
		
		while (i < questions_local.size()) {
			
			
			if (questions_local.get(i).type == Question.RESPONSE) {
				System.out.println("RESPONSE QUESTION: " + questions_local.get(i).question);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String answer = "";
				System.out.println("DIRECTIONS: Please type your response. If this a multiple part question, \n"
						+ "such as 'name the five first presidents,' add this symbol '<>' between answers");
				try {
					answer = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println("\n Checking answer: " + questions_local.get(i).checkAnswer(answer));
			} else if (questions_local.get(i).type == Question.MULTICHOICE) {
				System.out.println("MULTIPLE CHOICE QUESTION: " + questions_local.get(i).question);
				List<String>allOptions = ((MultiChoice)questions_local.get(i).answer).allOptions;
				System.out.println("CHOICES: ");
				for (String option: allOptions) System.out.println(option);
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				String answer = "";
				System.out.println("\nDIRECTIONS: Please retype the phrase that you select as your answer. \nIf there are multiple"
						+ "answers that you want to choose, use '<>' to signal between answers. Watch for typos!");
				try {
					answer = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				int numCorrect = questions_local.get(i).checkAnswer(answer);
				System.out.println("\nnumber of correct answers" + numCorrect);
			}			
			
			
			
			i++;
		}
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
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
		
		String query = "SELECT * FROM quizzes WHERE quizName = " + quizName;
		ResultSet rs = DBConnection.query(query);
		int quiz_id = 0;
		try {
			while(rs.next()) {
				quiz_id = rs.getInt(quiz_id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		while (true) {
			System.out.println("What kind of question would you like to add (Response or Multiple Choice)?");
			System.out.println("Type 'R' or 'MC' (without the ' ')");
			
			String questionType = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			try {
				questionType = br.readLine();
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
