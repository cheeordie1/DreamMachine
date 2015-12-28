package assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;


public class Quiz {
	// instance variables
	public ErrorMessages errors;
	public List<Question> questions;
	
	// database variables
	public int quiz_id;
	public int user_id; 
	public String name;
	public String description;
	public boolean single_page;
	public boolean random_questions;
	public boolean immediate_correct;
	public boolean practice_mode;
	public Date created_at;

	// static variables
	public static String TABLE_NAME = "quizzes";

	// error types
	public static String NAME_ERROR = "name";
	public static String DESCRIPTION_ERROR = "description";
	
	// error messages
	public static String NAME_EMPTY = "Name cannot be empty.";
	public static String DESCRIPTION_EMPTY = "Description cannot be empty.";
	
	/*
	 * Constructor for making a quiz on website
	 */
	public Quiz () {
		errors = new ErrorMessages();
	}
	
	/**
	 * Constructor for uploading quiz from database 
	 */
	public Quiz (ResultSet quizData) {
		try {
			quiz_id = quizData.getInt("quiz_id"); 
			name = quizData.getString("name");
			description = quizData.getString("description");
			user_id = quizData.getInt("user_id");
			single_page = quizData.getBoolean("single_page");
			random_questions = quizData.getBoolean("random_questions");
			practice_mode = quizData.getBoolean("practice_mode");
			created_at = quizData.getDate("created_at");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert the quiz into the quizzes table and assign the 
	 * quiz_id instance variable to the unique id generated by 
	 * adding the quiz to the table.
	 */
	public boolean save() {
		boolean errors = false;
		if (nameEmpty()) errors = true;
		if (descriptionEmpty()) errors = true;
		if (errors) return false;
		String update = "INSERT INTO " + TABLE_NAME + "(user_id, name, description," + 
		                "single_page, random_questions, " +
				        "immediate_correct, practice_mode) VALUES(?,?,?,?,?,?,?)";
		PreparedStatement stmt = DBConnection.beginStatement(update);
		try {
			stmt.setInt(1, user_id);
			stmt.setString(2, name);
			stmt.setString(3, description);
			stmt.setBoolean(4, single_page);
			stmt.setBoolean(5, random_questions);
			stmt.setBoolean(6, immediate_correct);
			stmt.setBoolean(7, practice_mode);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			return false;
		}
		quiz_id = DBConnection.update(stmt);
		if (quiz_id == 0) return false;
		return true; 
	}
	
	/**
	 * Update will change a db entry for a given quiz if the quiz
	 * has proper format and filled fields.
	 */
	public boolean update() {
		return false;
	}
	
	/**
	 * Delete will delete a db entry from the table by searching for
	 * its unique primary key quiz_id.
	 */
	public boolean delete() {
        if (quiz_id == 0) return false;
        String update = "DELETE FROM " + TABLE_NAME + 
                        " WHERE quiz_id = '" + quiz_id + "' LIMIT 1";
        DBConnection.update(update);
        return true;
	}
	
	/**
	 * Returns whether the given String parameter is empty.
	 * @param checkEmpty the String to check for emptiness
	 * @return true if String is empty, false otherwise
	 */
	private boolean isEmpty(String checkEmpty) {
		if (checkEmpty == null || checkEmpty.isEmpty() || 
			checkEmpty.matches("\\s*")) return true;
		return false;
	}
	
	/**
	 * Search for a quiz by its unique quiz_id
	 * @param quiz_id the id to search by
	 * @return the list of quizzes with the same id
	 */
	public static List<Quiz> searchByID(int quiz_id) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE quiz_id = '" + quiz_id + "'";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search for a quiz by its owner's user id
	 * @param user_id of the owner of the quiz
	 * @return the list of quizzes with the same user_id
	 */
	public static List<Quiz> searchByUserID(int user_id) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + user_id + "'";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search for a list of quizes by the id of the creator. Sort them by the date they were
	 * created at.
	 * @param user_id the creator to search for quizzes by
	 * @param ascending whether to sort the quizzes ascending or descending
	 * @return a list of quizzes made by a user with the user id sorted by created at
	 */
	public static List<Quiz> searchByUserIDSortByCreatedAt(int user_id, boolean ascending) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = '" + user_id + "' " +
				       "ORDER BY created_at " + (ascending ? "ASC" : "DESC" );
		System.out.println(query);
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search for a list of quizzes from the database. Return a list by the name
	 * of the quiz.Set substring to true if the search matches substrings
	 * @return  list of quizzes searched by name
	 */
	public static List<Quiz> searchByName(String name, boolean substring) {
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE name LIKE '" + 
					   (substring ? "%" : "") + name + (substring ? "%" : "") + "'";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search for a quiz in the database with a tag that contains the tag name. Able to
	 * set whether to search by substring.
	 * @param tagname name of tag to match in quiz search
	 * @param substring boolean whether to match by substring
	 * @return a list of quizzes that have matching tags
	 */
	public static List<Quiz> searchByTag(String tagname, boolean substring) {
		String query = "SELECT DISTINCT " + TABLE_NAME + ".* FROM " + TABLE_NAME +
				       " INNER JOIN " + Tag.TABLE_NAME + 
					   " ON " + Tag.TABLE_NAME + ".tag LIKE '" +
				   	   (substring ? "%" : "") + tagname + (substring ? "%" : "") + "'" +
					   " WHERE (" + TABLE_NAME + ".quiz_id = " + Tag.TABLE_NAME + ".quiz_id)";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search database for a list of quizzes with matching tag name or quiz name
	 * @param tag name of the tag to search for
	 * @param name of the quiz to search for
	 * @param substring wether or not to search by substring
	 * @return list of quizzes that have matching quiz name or tag
	 */
	public static List<Quiz> searchByTagOrName(String tag, String name, boolean substring) {
		String query = "SELECT DISTINCT " + TABLE_NAME + ".* FROM " + TABLE_NAME +
			       " INNER JOIN " + Tag.TABLE_NAME + 
				   " ON " + Tag.TABLE_NAME + ".tag LIKE '" +
			   	   (substring ? "%" : "") + tag + (substring ? "%" : "") + "'" +
				   " WHERE (" + TABLE_NAME + ".quiz_id = " + Tag.TABLE_NAME + ".quiz_id) " +
			   	   "OR ("+ TABLE_NAME + ".name LIKE '" + 
			   	   (substring ? "%" : "") + name + (substring ? "%" : "") + "')";
		System.out.println(query);
	    ResultSet rs = DBConnection.query(query);
	    return fromResultSet(rs);
	}
	
	/**
	 * Search the database for quizzes by the username of the creator. Able to set whether
	 * to search by substring or not.
	 * @param username the name of the quiz creator to search by
	 * @param substring whether or not to search by substring
	 * @return a list of quizzes that have matching creator name
	 */
	public static List<Quiz> searchByUsername(String username, boolean substring) {
		String query = "SELECT DISTINCT " + TABLE_NAME + ".* FROM " + TABLE_NAME +
				       " INNER JOIN " + User.TABLE_NAME + 
					   " ON " + User.TABLE_NAME + ".username LIKE '" +
					   (substring ? "%" : "") + username + (substring ? "%" : "") + "'" +
					   " WHERE (" + TABLE_NAME + ".user_id = " + User.TABLE_NAME + ".user_id)";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search the database for a list of quizzes that match the quiz name or creator name
	 * @param username name of the creator of the quiz
	 * @param name of the quiz to search by
	 * @param substring whether or not to search by substring
	 * @return list of quizzes that matches the specifications
	 */
	public static List<Quiz> searchByUsernameOrName(String username, String name, boolean substring) {
		String query = "SELECT DISTINCT " + TABLE_NAME + ".* FROM " + TABLE_NAME +
			       " INNER JOIN " + User.TABLE_NAME + 
				   " ON " + User.TABLE_NAME + ".username LIKE '" +
				   (substring ? "%" : "") + username + (substring ? "%" : "") + "'" +
				   " WHERE (" + TABLE_NAME + ".user_id = " + User.TABLE_NAME + ".user_id) " +
				   "OR ("+ TABLE_NAME + ".name LIKE '" + 
			   	   (substring ? "%" : "") + name + (substring ? "%" : "") + "')";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search the database for all quizzes that have matching tags or usernames
	 * @param tag the name of the tag to search by
	 * @param username the name of the user to search by
	 * @param substring whether or not to search by substring
	 * @return a list of quizzes that have matching tag or username
	 */
	public static List<Quiz> searchByTagOrUsername(String tag, String username, boolean substring) {
		String query = "SELECT DISTINCT " + TABLE_NAME + ".* FROM " + TABLE_NAME +
			       " INNER JOIN " + User.TABLE_NAME + 
				   " ON " + User.TABLE_NAME + ".username LIKE '" +
				   (substring ? "%" : "") + username + (substring ? "%" : "") + "'" +
				   " INNER JOIN " + Tag.TABLE_NAME + 
				   " ON " + Tag.TABLE_NAME + ".tag LIKE '" +
				   (substring ? "%" : "") + tag + (substring ? "%" : "") + "'" +
				   " WHERE (" + TABLE_NAME + ".user_id = " + User.TABLE_NAME + ".user_id) " + 
				   "OR (" + TABLE_NAME + ".quiz_id = " + Tag.TABLE_NAME + ".quiz_id)";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Search the database for all quizzes that have matching tags or usernames or quiz names
	 * @param tag the name of the tag to search by
	 * @param username the name of the user to search by
	 * @param name the name of the quiz to search by
	 * @param substring whether or not to search by substring
	 * @return a list of quizzes that have matching tag or username or quiz name
	 */
	public static List<Quiz> searchByTagOrUsernameOrName(String tag, String username, String name, boolean substring) {
		String query = "SELECT DISTINCT " + TABLE_NAME + ".* FROM " + TABLE_NAME +
			       " INNER JOIN " + User.TABLE_NAME + 
				   " ON " + User.TABLE_NAME + ".username LIKE '" +
				   (substring ? "%" : "") + username + (substring ? "%" : "") + "'" +
				   " INNER JOIN " + Tag.TABLE_NAME + 
				   " ON " + Tag.TABLE_NAME + ".tag LIKE '" +
				   (substring ? "%" : "") + tag + (substring ? "%" : "") + "'" +
				   " WHERE (" + TABLE_NAME + ".user_id = " + User.TABLE_NAME + ".user_id) " + 
				   "OR (" + TABLE_NAME + ".quiz_id = " + Tag.TABLE_NAME + ".quiz_id) " +
				   "OR ("+ TABLE_NAME + ".name LIKE '" + 
			   	   (substring ? "%" : "") + name + (substring ? "%" : "") + "')";
		ResultSet rs = DBConnection.query(query);
		return fromResultSet(rs);
	}
	
	/**
	 * Returns whether the name field is empty. It must contain a
	 * non whitespace character.
	 */
	public boolean nameEmpty() {
		if (isEmpty(name)) {
			errors.addError(NAME_ERROR, NAME_EMPTY);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns whether the description field is empty. It must contain a
	 * non whitespace character.
	 */
	public boolean descriptionEmpty() {
		if (isEmpty(description)) {
			errors.addError(DESCRIPTION_ERROR, DESCRIPTION_EMPTY);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns a list of quizzes from the result set. Could be empty
	 * @return List of quizzes from the result set
	 */
	private static List<Quiz> fromResultSet(ResultSet rs) {
		List<Quiz> quizzes = new ArrayList<Quiz>();
		if (rs == null) return quizzes;
		try {
			while (rs.next())
				quizzes.add(new Quiz(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return quizzes;
	}
}
