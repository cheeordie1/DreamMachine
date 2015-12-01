package assignment;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

public class Photo {
	/* instance variables */
	public Part part;
	public ErrorMessages errorMessages;

	/* database entries */
	public int id;
	public String ftype;
	public SerialBlob data;
	
	/* static variables */
	public static final int MAX_PHOTO_SZ = 1000000;
	public static final String TABLE_NAME = "photos";
	
	/* Error Message keys */
	public static final String PHOTO_ERROR = "photo";
	
	/* Error Messages */
	public static final String FILE_EMPTY = "Please upload a photo.";
	public static final String TYPE_MISMATCH = "File must be a .jpg or .png file.";
	public static final String FILE_TOO_LARGE = "File size must be < 1 MB.";
	public static final String FILE_FAIL = "File could not be loaded for some reason.";
	
	public Photo() {
		errorMessages = new ErrorMessages();
	}
	
	public Photo(ResultSet rs) {
		errorMessages = new ErrorMessages();
		try {
			id = rs.getInt("photo_id");
			data = new SerialBlob(rs.getBlob("data"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save a Photo object into the database and returns true if it worked or
	 * false otherwise. If save fails, errors are supplied to Photo errors.
	 * @return true if save works, false otherwise.
	 */
	public boolean save() {
		if (fileMissing()) return false;
		if (photoTypeWrong() || photoTooLarge()) return false;
		String query = "INSERT INTO " + TABLE_NAME + " (ftype, data) VALUES(?,?) ";
		PreparedStatement stmt = DBConnection.beginStatement(query);
		try {
			stmt.setString(1, ftype);
			stmt.setBlob(2, part.getInputStream());
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			errorMessages.addError(PHOTO_ERROR, FILE_FAIL);
			return false;
		}
		id = DBConnection.update(stmt);
		return true;
	}
	
	/**
	 * Attempts to update the photo entry in the database.
	 * @return true if update worked, false otherwise.
	 */
	public boolean update() {
		return true;
	}
	
	/**
	 * Destroys the photo entry from the database by finding it by
	 * id. Returns true if it works, false otherwise.
	 * @return true if destruction worked, false otherwise.
	 */
	public boolean destroy() {
        if (id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE user_id = " + id + " LIMIT 1";
        DBConnection.update (query);
		return true;
	}
	
	/**
	 * Search the database for a photo with the given id.
	 * @param photo_id the id of the photo to look for
	 * @return a Photo object of the given photo if it is found, null
	 *         otherwise
	 */
	public static List<Photo> searchById(int photo_id) {
		List<Photo> photos = new ArrayList<Photo>();
		String query = "SELECT * FROM " + TABLE_NAME + " WHERE photo_id = " + photo_id;
		ResultSet rs = DBConnection.query(query);
		if (rs == null) return photos;
		try {
			while (rs.next())
				photos.add(new Photo(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return photos;
	}
	
	/**
	 * Return whether the file part is present.
	 * @return true if file part is null, false otherwise
	 */
	private boolean fileMissing() {
		if (part == null || part.getSize() == 0) {
			errorMessages.addError(PHOTO_ERROR, FILE_EMPTY);
			return true;
		}
		return false;
	}
	
	/**
	 * Return whether the photo is less than 1 megabyte.
	 * @return true if photo too large, false otherwise
	 */
	private boolean photoTooLarge() {
		if (part.getSize() > MAX_PHOTO_SZ) {
			errorMessages.addError(PHOTO_ERROR, FILE_TOO_LARGE);
			return true;
		}
		return false;
	}
	
	/**
	 * Return whether a photo is the proper type format.
	 * @return true if photo is proper type, false otherwise
	 */
	private boolean photoTypeWrong() {
		String fileName = part.getSubmittedFileName();
		if (fileName.endsWith(".png")){
			ftype = "png";
			return false;
		} else if (fileName.endsWith(".jpg")) {
			ftype = "jpg";
			return false;
		}
		errorMessages.addError(PHOTO_ERROR, TYPE_MISMATCH);
		return true;
	}
	
	/**
	 * Return a base64 representation of the photo for use as the src of
	 * an img.
	 * @return string representation of the data in a photo.
	 */
	@Override
	public String toString() {
		String bData;
		try {
			bData = new String(data.getBytes(0, (int) data.length()));
		} catch (SerialException e) {
			e.printStackTrace();
			return "error";
		}
		String dataStr = "data:image/" + ftype + ";base64," + bData;
		return dataStr;
	}
}
