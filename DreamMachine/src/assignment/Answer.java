package assignment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Answer {
	// instance variables
	public ErrorMessages errorMessages;
	
	// database variables
	public int answer_id;
	public String answer;
	
	// static variables
	public static final String TABLE_NAME = "answers";
	public static final String DELIM = "=";
	public static final String SEPARATOR = ",";
	
	// error types
	public static final String ANSWER_ERROR = "answer";
	
	// error Strings
	public static final String ANSWER_EMPTY = "There is no answer";
	public static final String ANSWER_UNPARSEABLE = "Could not parse answer";

	public Answer() {
		errorMessages = new ErrorMessages();
	}
	
	public Answer(ResultSet rs) {
		try {
			answer_id = rs.getInt("answer_id");
			answer = rs.getString("answer");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return;
		}
	}
	
	/**
	 * Validates the answer format and saves the answer to the
	 * database.
	 * @return true if the save worked, false otherwise
	 */
	public boolean save() {
		if (answerEmpty()) return false;
		String update = "INSERT INTO " + TABLE_NAME + " (answer) VALUES(?)";
		PreparedStatement stmt = DBConnection.beginStatement(update);
		try {
			stmt.setString(1, answer);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
		answer_id = DBConnection.update(stmt);
		return true;
	}
	
	/**
	 * Update the answer to have new values in the database
	 * @return true if the update worked, false otherwise
	 */
	public boolean update() {
		return false;
	}
	
	/**
	 * Delete an answer from the database by its id
	 * @return
	 */
	public boolean delete() {
        if (answer_id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE answer_id = " + answer_id + " LIMIT 1";
        DBConnection.update (query);
		return true;
	}
	
	public static List<Answer> searchByID(int answer_id) {
		List<Answer> answers = new ArrayList<Answer>();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE answer_id = " + answer_id;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return answers;
		try {
			while (rs.next())
				answers.add(new Answer(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return answers;
	}
	
	public boolean answerEmpty() {
		if (answer == null || answer.isEmpty() || answer.matches("\\s*")) {
			errorMessages.addError(ANSWER_ERROR, ANSWER_EMPTY);
			return true; 
		}
		return false;
	}
}
