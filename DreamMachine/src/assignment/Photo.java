package assignment;

import javax.servlet.http.Part;

public class Photo {
	public int id;
	public String url;
	public Part part;
	public ErrorMessages errorMessages;
	
	/* Error Message keys */
	public static final String PHOTO_ERROR = "photo";
	
	/* Error Messages */
	public static final String TYPE_ERROR = "File must be a .jpg or .png file.";
	public static final String WRITE_ERROR = "File could not be written.";
	
	public Photo() {
		errorMessages = new ErrorMessages();
	}
	
	public void storePhoto() {
		
	}
	
	public boolean save() {
		id = 1;
		return true;
	}
	
	public boolean update() {
		return true;
	}
	
	public boolean destroy() {
		return true;
	}
}
