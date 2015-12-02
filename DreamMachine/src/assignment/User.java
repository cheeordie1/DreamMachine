package assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.security.*;

import javax.servlet.http.Part;

public class User {
    
    /* constants */
    private final int MIN_PASSWORD_SIZE = 8;
    private final int SALT_SIZE = 4;

    /* instance variables */
    public int id;
    public String password;
    public String repassword;
    public String retry;
    public Part photoPart;
    public ErrorMessages errorMessages;

    /* info for database entry */
    public String username;
    public String passwordDigest; 
    public String salt;
    public int photo_id;
    
    /* Error Key Strings */
	public static final String TABLE_NAME = "users";
	public static final String USERNAME_ERROR = "username";
	public static final String PASSWORD_ERROR = "password";
	public static final String PASSWORD_DUP_ERROR = "password_dup";
	public static final String PHOTO_ERROR = "photo";
	public static final String EMPTY_ERROR = "empty";
	public static final String LOGIN_ERROR = "login";
	
	/* Error Message Strings */
    public static final String USERNAME_UNAVAILABLE = 
        "That username is unavailable. Please try another.";
    public static final String USERNAME_EMPTY = 
        "Please enter a username.";
    public static final String USERNAME_INVALID = 
        "Please limit your username to only characters and numbers. No whitespace.";
    public static final String PASSWORD_EMPTY = 
    	"Please enter a password.";
    public static final String REPASSWORD_EMPTY = 
    	"Please re-enter a password.";
    public static final String PASSWORD_INSUFFICIENT = 
        "Please choose a password at least 8 characters in length.";
    public static final String PASSWORD_MATCH_FAILED =
        "The two password entries don't match. Please try again.";
    public static final String LOGIN_FAILED =
    	"Could not find the specified username password combination.";
    public static final String LOGIN_EMPTY =
    	"Please enter a username and password to login.";
    
    public User(ResultSet rs) {
    	try {
			id = rs.getInt("user_id");
	    	username = rs.getString("username");
	    	salt = rs.getString("salt");
	    	passwordDigest = rs.getString("digest");
	    	photo_id = rs.getInt("photo_id");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public User(String username) {
        this.errorMessages = new ErrorMessages();
        this.username = username;
    }
    
    /** 
     * Attempts to save the given username and password combination. If the
     * two password attempts (password and retry) don't match, or any other
     * criteria for a valid password isn't met, the mapping is not created. 
     */
    public boolean save() {
        boolean hasEmpty = true;
        hasEmpty = hasEmpty && usernameEmpty() &&
                  passwordEmpty() && repasswordEmpty();
        if (hasEmpty)
        	return false;
        if(usernameUnavailable() ||
           usernameInvalid() || 
           passwordInsufficient() ||
           passwordMatchFailed())
        	return false;
        else {
        	/* save photo */
        	Photo photo = new Photo();
        	photo.part = photoPart;
        	if (!photo.save())
        		{
        			for (String error : photo.errorMessages.errors.get(Photo.PHOTO_ERROR))
        				errorMessages.addError(PHOTO_ERROR, error);
        			return false;
        		}
        	photo_id = photo.id;
        	
            /* create a new entry for this username password pair */
            String entry =
                "INSERT INTO " + TABLE_NAME + "(username,salt,digest,photo_id) VALUES (" +
                "'" + this.username + "'," +
                "'" + this.salt + "'," +
                "'" + this.passwordDigest + "'," +
                photo_id +
                ")";
            id = DBConnection.update(entry);
        }

        return true;
    }

    /** 
     * Update will change a db entry for a given user if the user has valid
     * format and fields. 
     */
    public boolean update() {
        return false;
    }

    /**
     * Destroy will delete a user entry from the database by its id.
     * @return true if deleted, false otherwise  
     */
    public boolean destroy() {
        if (id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE user_id = " + id + " LIMIT 1";
        DBConnection.update (query);
        return true;
    }
    
    /**
     * Static method to search for a User by their user id.
     * @return a list containing all of the users in the search
     *         result.
     */
    public static List<User> searchById(int id) {
    	List<User> users = new ArrayList<User>();
    	String query = "SELECT * FROM " + TABLE_NAME + 
    	               " WHERE user_id = " + id;
    	ResultSet rs = DBConnection.query(query);
    	try {
			while (!rs.next())
				users.add(new User(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return users;
    }
    
    /**
     * Static method to search for a User by their username.
     * @return a list containing all of the users in the search
     *         result.
     */
    public static List<User> searchByUsername(String username) {
    	List<User> users = new ArrayList<User>();
    	String query = "SELECT * FROM " + TABLE_NAME + 
    	               " WHERE username LIKE " + "'" + username + "'";
    	ResultSet rs = DBConnection.query(query);
    	try {
			while (rs.next())
				users.add(new User(rs));
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("hi");
		}
    	return users;
    }
    
    /**
     * Static method to search for a User by a substring of their username.
     * @return a list containing all of the users in the search
     *         result.
     */
    public static List<User> searchBySubstring(String usernameSub) {
    	List<User> users = new ArrayList<User>();
    	String query = "SELECT * FROM " + TABLE_NAME + 
    	               " WHERE username LIKE " + "'%" + usernameSub + "%'";
    	ResultSet rs = DBConnection.query(query);
    	try {
			while (rs.next())
				users.add(new User(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return users;
    }
    
    
    /**
     * Method checks whether a given password hashes to the correct password
     * digest, indicating that it is most likely the correct password.
     * @param checkPassword the String to check for correct password
     * @return true if the password's sha-256 hash matches the actual password's
     *         digest
     */
    public boolean checkPassword(String checkPassword) {
    	checkPassword += salt;
        try {
    	  MessageDigest md = MessageDigest.getInstance("SHA-256");
    	  String checkDigest = hexToString(md.digest(checkPassword.getBytes()));
    	  return checkDigest.equals(passwordDigest);
        } catch (NoSuchAlgorithmException ignored) {}
        return false;
    }
    
    /* Takes a given password and creates a salt and digest for the given
     * password. The User class will only store the salt and digest generated
     * for the password, not the password itself. */
    public void setPassword(String password) {
        /* Generate a random salt */
        this.password = password;
        Random random = new Random();
        this.salt = "";
        for(int i = 0; i < SALT_SIZE; i++)
            this.salt += Integer.toHexString(random.nextInt());
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            password += this.salt;
            this.passwordDigest = hexToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException ignored) {}
    }

    /* ensures the username is not null or empty. If the given username is null
     * or empty, this returns true. */
    private boolean usernameEmpty() {
    	if (username == null || username.length() == 0) {
    		errorMessages.addError(USERNAME_ERROR, USERNAME_EMPTY);
    		return true;
    	}
        return false;
    }
    
    /* ensures the password is not null or empty. If the given password is null
     * or empty, this returns true. */
    private boolean passwordEmpty() {
    	if (password == null || password.length() == 0) {
    		errorMessages.addError(PASSWORD_ERROR, PASSWORD_EMPTY);
    		return true;
    	}
        return false;
    }
    
    /* ensures the username is not null or empty. If the given username is null
     * or empty, this returns true. */
    private boolean repasswordEmpty() {
    	if (repassword == null || repassword.length() == 0) {
    		errorMessages.addError(PASSWORD_DUP_ERROR, REPASSWORD_EMPTY);
    		return true;
    	}
        return false;
    }
    
    /* ensures the username is not already in use. If the given username is
     * already in use, this returns true. */
    private boolean usernameUnavailable() {
        /* Query the database for the given username. If there, return false */ 
        String query = "SELECT * FROM " + TABLE_NAME +
                       " WHERE username = '" + username + "'";
        ResultSet rs = DBConnection.query(query);
        
        /* if result set isn't empty, this username can't be used. */
        try {
            if(rs.first()) {
                this.errorMessages.addError(USERNAME_ERROR, USERNAME_UNAVAILABLE);
                return true;
            }
        } catch(SQLException ignored) {}
        return false;
    }
    
    /* ensure password is greater than 8 characters. If the username is fewer
     * than 8 characters, this returns true. */
    private boolean passwordInsufficient() {
        if(password.length() < MIN_PASSWORD_SIZE) {
            this.errorMessages.addError(PASSWORD_ERROR, PASSWORD_INSUFFICIENT);
            return true;
        }
        return false;
    }
    
    /* ensures the username is valid. If the username is invalid (there are
     * any symbols or spaces) this returns true */
    private boolean usernameInvalid() {
    	if (username.matches(".*\\s.*")) {
    		this.errorMessages.addError(USERNAME_ERROR, USERNAME_INVALID);
        	return true;
    	}
    	return false;
    }
    
    /* ensure that the user entered the password correctly the second time for
     * added unsurance. This returns true is the passwords don't match. */
    private boolean passwordMatchFailed() {
        if(!password.equals(repassword)) {
            this.errorMessages.addError(PASSWORD_DUP_ERROR, PASSWORD_MATCH_FAILED);
            return true;
        }
        return false;
    }

    /** utility methods */
    public static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
}
