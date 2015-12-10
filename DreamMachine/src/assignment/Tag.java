package assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tag {

	// database variables
	public int tag_id;
	public int quiz_id;
	public String tag;
	
	// static variables
	public static String TABLE_NAME = "tags";
	
	public Tag() {
	}
	
	public Tag(ResultSet tagData) {
		try {
			tag_id = tagData.getInt("tag_id");
			quiz_id = tagData.getInt("quiz_id");
			tag = tagData.getString("tag");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Validates a tag and then saves it
	 * @return true if the tag has been saved,
	 *         false otherwise
	 */
	public boolean save() {
		if (duplicateTag()) return false;
		String update = "INSERT INTO " + TABLE_NAME + "(quiz_id, tag) VALUES(" +
		                quiz_id + ",'" + tag + "')";
        tag_id = DBConnection.update(update);
		return true;
	}
	
	/**
	 * Updates a tag
	 * @return whether the tag has been updated
	 */
	public boolean update() {
		return false;
	}
	
	/**
	 * Deletes a tag from the database
	 * @return whether the tag has been deleted
	 */
	public boolean delete() {
        if (tag_id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE tag_id = '" + tag_id + "' LIMIT 1";
        DBConnection.update (query);
		return true;
	}

	/**
	 * Searches the database for tags by quiz id
	 * @return a list of tags by their id
	 */
	public static List<Tag> searchByQuizID(int quiz_id) {
		List<Tag> tags = new ArrayList<Tag>();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE quiz_id = '" + quiz_id + "'";
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return tags;
		try {
			while (rs.next())
				tags.add(new Tag(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	/**
	 * Searches the database for tags by tag id
	 * @return a list of tags by their id
	 */
	public static List<Tag> searchByID(int tag_id) {
		List<Tag> tags = new ArrayList<Tag>();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE tag_id = '" + tag_id + "'";
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return tags;
		try {
			while (rs.next())
				tags.add(new Tag(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	public static List<Tag> searchByTagQuizID(String tag, int quiz_id) {
		List<Tag> tags = new ArrayList<Tag>();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE tag = '" + tag +
				       "' AND " + "quiz_id = '" + quiz_id + "'";
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return tags;
		try {
			while (rs.next())
				tags.add(new Tag(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tags;
	}
	
	public boolean duplicateTag() {
		List<Tag> dups = searchByTagQuizID(tag, quiz_id);
		if (dups.isEmpty())
			return false;
		return true;
	}
}
