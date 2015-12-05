package assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

public class Question {
	//instance variables
	public ErrorMessages errorMessages;
	public Part photoPart;
	
	// database variables
	public int question_id;
	public int quiz_id;
	public int answer_id;
	public int photo_id;
	public Type question_type; 
	public String question;
	
	public enum Type {
	    RESPONSE, MULTICHOICE, MATCHING
	}
	
	// static variables
	public static String TABLE_NAME = "questions";
	public static String RESPONSE = "response";
	public static String MULTICHOICE = "multichoice";
	public static String MATCHING = "matching";
	
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
		    question_type = (Type) questionData.getObject("question_type");
			question = questionData.getString("question");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Validates the question format.
	 * It adds a new question to the database Question Table. 
	 * @return true if the save worked, false otherwise
	 */
	public boolean save() {
		if (questionEmpty()) return false;

    	/* save photo */
		if (photoPart != null) {
			Photo photo = new Photo();
    		photo.part = photoPart;
    		if (!photo.save()) {
    			List<Answer> answers = Answer.searchByID(answer_id);
    			if (!answers.isEmpty()) answers.get(0).delete();
    			for (String error : photo.errorMessages.errors.get(Photo.PHOTO_ERROR))
    				errorMessages.addError(Photo.PHOTO_ERROR, error);
    			return false;
    		}
        	photo_id = photo.id;
		} else photo_id = -1;
		String update = "INSERT INTO "+ TABLE_NAME + " (quiz_id, answer_id, photo_id, question_type, question) VALUES(?,?,?,?,?)";
		PreparedStatement stmt = DBConnection.beginStatement(update);
		try {
			stmt.setInt(1, quiz_id);
			stmt.setInt(2, answer_id);
			stmt.setInt(3, photo_id);
			stmt.setObject(4, question_type);
			stmt.setString(5, question);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
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
	
	/**
	 * Delete a question from the database by id. Deleting a question will
	 * delete its answer since they are 1:1.
	 * @return true if delete worked, false otherwise
	 */
	public boolean delete() {
        if (question_id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE question_id = " + question_id + " LIMIT 1";
        DBConnection.update (query);
        List<Answer> answers = Answer.searchByID(answer_id);
        if (answers.isEmpty())
        	return true;
        answers.get(0).delete();
		return true;	
	}
	
	public static List<Question> searchByQuizID(int quiz_id, boolean order) {
		List<Question> questions = new ArrayList<Question>();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE quiz_id = " + quiz_id;
		if (order) query += " ORDER BY question_id ASC";
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
		if (question == null || question.isEmpty() || question.matches("\\s*")) {
			errorMessages.addError(Question.QUESTION_ERROR, Question.QUESTION_EMPTY);
		}
		return false;
	}
}
