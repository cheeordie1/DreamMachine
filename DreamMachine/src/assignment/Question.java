package assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

public class Question {
	//instance variables
	public ErrorMessages errorMessages;
	public Answer answer;
	public Part photoPart;
	
	// database variables
	public int question_id;
	public int quiz_id;
	public int answer_id;
	public int photo_id;
	public Type type; 
	public String question;
	
	public enum Type {
	    RESPONSE, MULTICHOICE, MATCHING 
	}
	
	// static variables
	public static String TABLE_NAME = "questions";
	
	// error types
	public static String QUESTION_ERROR = "question";
	
	// error messages
	public static String QUESTION_EMPTY = "You need to enter a question.";
	
	public Question() {
	  errorMessages = new ErrorMessages();
	}
	
	public Question (ResultSet questionData) {
		try {
			question_id = questionData.getInt("question_id");
			quiz_id = questionData.getInt("quiz_id");
			answer_id = questionData.getInt("answer_id");
			photo_id = questionData.getInt("photo_id");
		    type = (Type) questionData.getObject("type");
			question = questionData.getString("question");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Validates the question format.
	 * If the question is already in the database then it 
	 * replaces the entry. Otherwise it adds a new quiz
	 * to the DBConnection.Question Table. 
	 * @return true if the save worked, false otherwise
	 */
	public boolean save() {
		if (questionEmpty()) return false;
		
		/* save answer */
		if (!answer.save()) {
			for (String errorType : answer.errorMessages.getKeys())
    		return false;
		}
		answer_id = answer.answer_id;
    	/* save photo */
    	Photo photo = new Photo();
    	photo.part = photoPart;
    	if (!photo.save()) {
    		for (String error : photo.errorMessages.errors.get(Photo.PHOTO_ERROR))
    			errorMessages.addError(Photo.PHOTO_ERROR, error);
    		return false;
    	}
    	photo_id = photo.id;
		String update = "INSERT INTO questions (quiz_id, answer_id, photo_id, type, question) VALUES(?,?,?,?,?)";
		PreparedStatement stmt = DBConnection.beginStatement(update);
		stmt.setInt(1, quiz_id);
		stmt.setInt(2, answer_id);
		stmt.setInt(3, photo_id);
		stmt.setObject(4, type);
		stmt.setString(5, question);
		quiz_id = DBConnection.update(stmt);
		return true;
	}
	
	/**
	 * Update the quiz question to have new values in the database
	 * @return true if the update worked, false otherwise
	 */
	public boolean update() {
		return false;
	}
	
	
	public boolean delete() {
        if (question_id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE user_id = " + question_id + " LIMIT 1";
        DBConnection.update (query);
		return true;	
	}
	
	public static List<Question> searchByUserID(int user_id) {
		List<Question> questions = new ArrayList<Question>();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = " + user_id;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return questions;
		try {
			while (rs.next())
				questions.add(new Question(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return questions;
	}
	
	/**
	 * Check whether the question as been filled out.
	 * a quiz. If not, we can't add a question
	 */
	public boolean questionEmpty() {
		if (question == null || question.isEmpty()) {
			errorMessages.addError(Question.QUESTION_ERROR, Question.QUESTION_EMPTY);
		}
		return false;
	}
	
	@Override
	public String toString(){
		return "id: " + question_id + " Type: " + type + " Question: " + question +
			   " Answer: " + answer;
	}
}
