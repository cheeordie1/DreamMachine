package assignment;

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
	
	
	
	
	public static void main (String args []) {

		DBConnection.connect();
		
		//create a user
		
		int user_id = 1; 
		String quizName = "Quiz";
		String description = "This is a quiz";
		
		String responseQuestion = "What state is Chicago located?";
		String responseAnswers = "Illinois" + Answer.SEPERATOR + "The Windy City";
		Response responseResponse = new Response(responseAnswers); 

		String responseUserAnswer = "Illinois";
		String responseWrongUserAnswer = "Blah";
		Question.Type type = Question.Type.RESPONSE;
	
		Answer answer = createAnswer(Question.Type.RESPONSE, responseAnswers);
		Quiz quiz = createQuiz(user_id, quizName, description);
		Question question = createQuestion(quiz.quiz_id, responseQuestion, type, answer);
		

		System.out.println(question.checkAnswer(responseUserAnswer));
		System.out.println(question.checkAnswer(responseWrongUserAnswer));	
	}	
}
