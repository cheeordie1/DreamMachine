package assignment;

import javax.servlet.http.Part;

public class Photo {
	/* instance variables */
	public Part part;
	public ErrorMessages errorMessages;

	/* database entries */
	public int id;
	public String url;
	
	/* Error Message keys */
	public static final String PHOTO_ERROR = "photo";
	
	/* Error Messages */
	public static final String EMTPY_ERROR = "File Part could not be found.";
	public static final String TYPE_ERROR = "File must be a .jpg or .png file.";
	public static final String WRITE_ERROR = "File could not be written.";
	
	public Photo() {
		errorMessages = new ErrorMessages();
	}
	
	public boolean save() {
		if (part == null) {
			errorMessages.addError(PHOTO_ERROR, EMPTY_ERROR);
			return false;
		}
		if (!storePhoto ()) return false;
		id = 1;
		return true;
	}
	
	public boolean update() {
		return true;
	}
	
	public boolean destroy() {
		return true;
	}
	
	private boolean storePhoto() {
		String partName = ;
		return true;
	}
}
