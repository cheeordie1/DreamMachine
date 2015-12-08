package assignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import java.sql.Timestamp;
public class Functionality {
	
	static int user_id; 
	static final String QUIZ_TAKE_PROMPT = "\nDIRECTIONS: Enter the number by a quiz to take it.";
	static final String PROMPT = "\nDIRECTIONS: "
								+ "\nType 1 to take quiz. "
								+ "\nType 2 to make quiz. "
								+ "\nType 3 to switch users. "
								+ "\nType 4 to see user history. "
								+ "\nType 5 to send/view challenges"
								+ "\nType 0 to quit";
	static final String WELCOME_MESSAGE = "\nWelcome to Dream Creatures Quiz Making Creating Terminal Program of Sadness";
	static final String SELECT_USER = "\nSelect user account 1, 2, or 3";
	static final String SPACER = "             ";
	private static final int NUM_OPTIONS = 5;
	private static final int MAX_USERS = 3;

	
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
				case 4:
					userHistory();
					break;
				case 5:
					challenges();
					break;
			}
		}
		
		System.out.println("Thanks for playing!");
	}
	
	private static void challenges() {
		System.out.println("\nDIRECTIONS: Type 1 to view your challenges, Type 2 to send a challenge.");
		System.out.print("Your choice: ");
		br = new BufferedReader(new InputStreamReader(System.in));
		int challengeChoice;
		while (true) {
			try {
				challengeChoice = Integer.parseInt(br.readLine());
				if (challengeChoice == 1) {
					viewChallenges();
					break;
				} else if (challengeChoice == 2) {
					sendChallenge();
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.print("Please enter a valid integer between 1 and 2: ");
		}
	}
	
	private static void viewChallenges() {
		ResultSet quizzesChallenged = Challenges.getChallengedQuizzes(user_id);
		try {
			while (quizzesChallenged.next()){
				System.out.println("You were challenged by USER " + quizzesChallenged.getInt("sender_user_id")
									+ " to take the quiz titled '" + quizzesChallenged.getString("link") + "'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void sendChallenge() {
		System.out.println("\nWho would you like to send a challenge to?");
		System.out.print("Please enter a valid user");
		for (int user = 1; user <=MAX_USERS; user++) {
			if (user_id == user) continue;
			System.out.print(" " + user);
		}
		System.out.print(": ");

		int receiver;
		br = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			try {
				receiver = Integer.parseInt(br.readLine());
				if (receiver > 0 && receiver <= MAX_USERS && receiver != user_id) break;
			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.print("Please enter a valid user");
			for (int user = 1; user <=MAX_USERS; user++) {
				if (user_id == user) continue;
				System.out.print(" " + user);
			}
			System.out.print(": ");
		}
		
		int challengeQuiz;
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Select Quiz to Send");
		ArrayList<Quiz>quizzes = populateQuizzesFromDB();
		if(quizzes.isEmpty()) {
			makeSampleQuiz();
			quizzes = populateQuizzesFromDB();
		}
		listQuizzes(quizzes);
		
		System.out.print("Your choice: ");
		while (true) {
			try {
				challengeQuiz = Integer.parseInt(br.readLine());
				if (challengeQuiz > 0 && challengeQuiz < quizzes.size()) break;
			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.print("Please enter a valid quiz choice: ");
		}
		
		Challenges.save(user_id, receiver, quizzes.get(challengeQuiz-1).name);
		System.out.println("UPDATE: CHALLENGE SENT!\n");
	}
	
	
	private static void userHistory() {
		int quizzesMade = Quiz.searchByUserID(user_id).size();
		int quizzesTaken = Score.searchByUserID(user_id).size();
		
		System.out.println("\nhistory for user" + user_id);
		System.out.println("Quizzes made: " + quizzesMade);
		System.out.println("Quizzes taken: " + quizzesTaken + "\n");
	}
	
	private static int getUser() {
		int user;
		br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println(SELECT_USER);
		while (true) {
			System.out.print("Your choice: ");
			try {
				String line = br.readLine();
				if (!line.equals(null) && !line.isEmpty()) {
					user = Integer.parseInt(line);
					if (user >= 1 && user <= MAX_USERS) return user;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}	
			System.out.println("Please enter a valid integer between 1 and " +  MAX_USERS);
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

		ArrayList<Quiz>quizzes = populateQuizzesFromDB();
		if(quizzes.isEmpty()) {
			makeSampleQuiz();
			quizzes = populateQuizzesFromDB();
		}
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
		int totalAnswers = 0;
		System.out.println("\nLet's begin the quiz!");

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
						System.out.print("Letter choice " + (mindex+1) + " of " + numAnswers+ ": ");
						try {
							String line = br.readLine();
							if (!line.isEmpty()) {
								if (mindex != 0) answer += "<>";
								int index = ((int) line.toUpperCase().charAt(0) - 'A');
								if (index >= 0 && index < allOptions.size()) answer += allOptions.get(index);
							}
						} catch (IOException e) {
							e.printStackTrace();
						}	
					}
					break; 	
				case Question.MATCHING:
					System.out.println("\n"+(i+1) + ") MATCHING QUESTION: " + currQuestion.question);
					
					List<String> leftOptions = ((Matching) currQuestion.answer).leftOptions;
					List<String> rightOptions = ((Matching) currQuestion.answer).rightOptions;
					Map<String,String> matches = ((Matching) currQuestion.answer).matches;
					int max = Math.max(leftOptions.size(), rightOptions.size());

					for (int index = 0; index < max; index++) {
						String leftWord = "";
						String rightWord = "";
						if (leftOptions.size() > index)
							leftWord = leftOptions.get(index);
						if (rightOptions.size() > index)
							rightWord = rightOptions.get(index);
						System.out.println(leftWord + SPACER + rightWord);
					}
					
					numAnswers = matches.size();
					for (int mindex = 0; mindex < numAnswers; mindex++) {
						System.out.println("Match Response " + (mindex+1) + " of " +numAnswers+ ": ");
						try {
							System.out.println("Left-side of match.. ");
							String line = br.readLine();
							if (!line.isEmpty()) {
								if (mindex != 0) answer += "<>";
								answer += line;
								answer += "=";
								System.out.println("Right-side of match.. ");
								line = br.readLine();
								if (!line.isEmpty()) {
									answer += line;
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				default: break; 
			}
			numCorrect = currQuestion.checkAnswer(answer);
			System.out.println("Correctly answered " + numCorrect + " of " + numAnswers);
			totalCorrect += numCorrect;
			totalAnswers+=numAnswers; 

		}
		
		System.out.println("You recieved a total of " + totalCorrect + " out of " + totalAnswers);
		return totalCorrect;
	}

	private static void showSummaryPage(Quiz quiz) {
		List<Score> scores = Score.searchByQuizID(quiz.quiz_id);
		
		System.out.println("\n" + quiz.name + "");
		System.out.println(quiz.description);
		System.out.println("High Score: " + QuizStats.highScore(scores) + " // Lowest Score: " + QuizStats.lowScore(scores) + " // Average Score: " + QuizStats.averageScore(scores) +" // Times Played: " + QuizStats.timesPlayed(scores));
		
		System.out.println("\nLeaderboard: ");
		ArrayList<Score> highScores = QuizStats.highestPerformers(scores);
		for (int i = 0; i < highScores.size(); i++) {
			Score currScore = highScores.get(i);
			System.out.print(currScore.startTime);
			System.out.print("  user" + currScore.user_id);
			System.out.print("  " + currScore.score);
			System.out.println("  " + currScore.getDuration() + "sec");
		}
		
		System.out.println("\nTop Performers of Past Day: ");
		ArrayList<Score> highScoresDay = QuizStats.highestPerformersPastDay(scores);
		for (int i = 0; i < highScoresDay.size(); i++) {
			Score currScore = highScoresDay.get(i);
			System.out.print(currScore.startTime);
			System.out.print("  user" + currScore.user_id);
			System.out.print("  " + currScore.score);
			System.out.println("  " + currScore.getDuration() + "sec");
		}
				
		System.out.println("\nYour Past Performances: ");
		ArrayList<Score> pastScores = QuizStats.pastPerformances(user_id, quiz.quiz_id);
		for (int i = 0; i < pastScores.size(); i++) {
			Score currScore = pastScores.get(i);
			System.out.print(currScore.startTime);
			System.out.print("  " + currScore.score + " Points");
			System.out.println("  " + currScore.getDuration() + "sec");

		}
		
		System.out.println("\nRecent Performances: ");
		ArrayList<Score> pastPerformances = QuizStats.recentPerformances(scores);
		for (int i = 0; i < pastPerformances.size(); i++) {
			Score currScore = pastPerformances.get(i);
			System.out.print(currScore.startTime);
			System.out.print("  user" + currScore.user_id);
			System.out.print("  " + currScore.score);
			System.out.println("  " + currScore.getDuration() + "sec");
		}
		
		System.out.print("\nHit enter to begin taking quiz");
		
		try {
			br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void takeQuiz() {
		Quiz currQuiz = selectQuiz();
		showSummaryPage(currQuiz);

		Date startTime = new Date();
		Timestamp startTimestamp = new Timestamp(startTime.getTime());
		int score = playQuiz(currQuiz); 
		String insertStr = "INSERT INTO scores (quiz_id, user_id, score, start_time) value(" +
							currQuiz.quiz_id +","+ user_id +","+ score +", '"+ startTimestamp +"');";
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
		System.out.print("ENTER QUIZ NAME: ");
		try {
			quizName = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("ENTER QUIZ DESCRIPTION: ");
		try {
			quizDescription = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Quiz quiz = createQuiz(user_id, quizName, quizDescription);
		addQuestions(quiz.quiz_id);
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
			System.out.println("What kind of question would you like to add? \n"
					+ "Type R for Response\n"
					+ "Type MC for Multiple Choice\n"
					+ "Type M for Matching\n"
					+ "Type 'quit' to quit\n");
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
			if (questionType.equals("M")) type = 3;
			
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
				
				System.out.println("DIRECTION: Please type the options and the answer on one line. "
						+ "\nSeparate the options using a comma and then separate the options from the answer using this symbol '<>'"
						+ "\nEXAMPLE: 'choiceA,choiceB,choiceC<>choiceA,choiceC'");
				try{
					answerString = br.readLine();
					answer = new MultiChoice(answerString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (type == 3) {
				System.out.println("DIRECTIONS: Matching Questions will be of the format\n"
						+ "high         light\n"
						+ "heavy       hate\n"
						+ "love        low\n"
						+ "and quiz-takers will need to match an option on the left side \n"
						+ "to an option on the right side. \n");
				try{
					System.out.println("How many options on the left side?");
					int leftOptNum = Integer.valueOf(br.readLine());
					
					String leftOptStr = "";
					for (int i = 0; i < leftOptNum; i++) {
						if (i != 0) leftOptions += ",";
						System.out.println("left option " + i + ": ");
						leftOptions += br.readLine();
					}
					System.out.println("Type in right options seperated by commas: (ex. 'option1b,option2b,option3b')");
					String rightOptStr = br.readLine();
					System.out.println("Type in matches between two options with a '=' in between"
							+ "them : (ex. 'option1=option2b,option3=option1b')");
					String matchStr = br.readLine();
					answerString = leftOptStr + Answer.DELIM + rightOptStr + Answer.DELIM + matchStr;
					answer = new Matching(answerString);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			createQuestion (quiz_id, question, type, answer);
		}
	}


	public static ArrayList<Quiz> populateQuizzesFromDB() {
		ArrayList<Quiz> allQuizzes = new ArrayList<Quiz>();
		
		String query_selectall = "SELECT * FROM quizzes";
		ResultSet rs = DBConnection.query(query_selectall);
		if (rs.equals(null)) return allQuizzes;
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
		Quiz quiz = createQuiz(user_id, quizName, description);

		String responseQuestion = "Who teaches CS108?";
		String responseAnswers = "Patrick Young" + Answer.SEPERATOR + "Professor Young" + Answer.SEPERATOR + "Patrick";
		Answer RESPONSE_answer = createAnswer(Question.RESPONSE, responseAnswers);
		createQuestion(quiz.quiz_id, responseQuestion, Question.RESPONSE, RESPONSE_answer);

		String responseMultiAnswerQuestion = "Name the three introductory CS courses";
		String responseMultiAnswerAnswers = "CS106A" + Answer.SEPERATOR + "CS 106A" + Answer.DELIM + "CS106B" + Answer.SEPERATOR + "CS 106B" + Answer.DELIM + "CS106X" + Answer.SEPERATOR + "CS 106X";
		Answer RESPONSEMULTI_answer = createAnswer(Question.RESPONSE, responseMultiAnswerAnswers);
		createQuestion(quiz.quiz_id, responseMultiAnswerQuestion, Question.RESPONSE, RESPONSEMULTI_answer);

		String multipleQuestion = "Who teaches CS106A?";
		String multipleAnswers = "Patrick Young" + Answer.SEPERATOR + "Mehran" + Answer.SEPERATOR + "Keith" + Answer.SEPERATOR + "Cynthia Bailey" + Answer.DELIM + "Mehran" + Answer.SEPERATOR + "Keith";
		Answer MULTIPLE_answer = createAnswer (Question.MULTICHOICE, multipleAnswers);
		createQuestion (quiz.quiz_id, multipleQuestion, Question.MULTICHOICE, MULTIPLE_answer);
		
		String matchingQuestion = "Match opposites.";
		String leftOptions = "hot" + Answer.SEPERATOR + "low" + Answer.SEPERATOR + "strong";
		String rightOptions = "high" + Answer.SEPERATOR + "cold" + Answer.SEPERATOR + "weak";
		String matches = "hot=cold" + Answer.SEPERATOR + "low=high" + Answer.SEPERATOR + "strong=weak";
		Answer MATCHING_answer = createAnswer (Question.MATCHING, leftOptions + Answer.DELIM + rightOptions + Answer.DELIM + matches);
		createQuestion(quiz.quiz_id, matchingQuestion, Question.MATCHING, MATCHING_answer);
		
		return quiz; 
	}	
}
