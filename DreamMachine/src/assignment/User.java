package assignment;

import java.sql.*;
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
    public ErrorMessages errorMessages;

    /* info for database entry */
    public String username;
    public String passwordDigest; 
    public String salt;
    public Part photoPart;
    
    /* Error Key Strings */
	public static final String TABLE_NAME = "users";
	public static final String USERNAME_ERROR = "username";
	public static final String PASSWORD_ERROR = "password";
	public static final String PASSWORD_DUP_ERROR = "password_dup";
	public static final String PHOTO_ERROR = "photo";
	public static final String EMPTY_ERROR = "empty";
	
	/* Error Message Strings */
    public static final String USERNAME_UNAVAILABLE = 
        "That username is unavailable. Please try another.";
    public static final String USERNAME_EMPTY = 
        "Please enter a username.";
    public static final String USERNAME_INVALID = 
        "Please limit your username to only characters and numbers.";
    public static final String PASSWORD_EMPTY = 
    	"Please enter a password.";
    public static final String REPASSWORD_EMPTY = 
    	"Please re-enter a password.";
    public static final String PASSWORD_INSUFFICIENT = 
        "Please choose a password at least 8 characters in length.";
    public static final String PASSWORD_MATCH_FAILED =
        "The two password entries don't match. Please try again.";
    
    public User(ResultSet rs) {
    	try {
        	if (!rs.next())
        		return;
			id = rs.getInt("user_id");
	    	username = rs.getString("username");
	    	salt = rs.getString("salt");
	    	passwordDigest = rs.getString("passwordDigest");
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
    public boolean save(/* TODO add photo saving */) {
        boolean success = true;
        if (usernameEmpty() || passwordEmpty() || repasswordEmpty())
        	return false;
        if(usernameUnavailable() ||
           usernameInvalid() || 
           passwordInsufficient() ||
           passwordMatchFailed())
        	success = false;
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
        		
        	
            /* create a new entry for this username password pair */
            String entry =
                "INSERT INTO " + TABLE_NAME + "(username,salt,digest,photo_id) VALUES (" +
                "'" + this.username + "'," +
                "'" + this.salt + "'," +
                "'" + this.passwordDigest + "'," +
                photo.id +
                ")";
            System.out.println(entry);
            ResultSet rs = DBConnection.update(entry);
            try {
				assert (rs.first() == true);
				id = rs.getInt("user_id");
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }

        return success;
    }

    public boolean update() {
        return false;
    }   

    public boolean destroy() {
        if (id == 0) return false;
        String query = "DELETE FROM " + TABLE_NAME + 
                       " WHERE user_id = " + id + " LIMIT 1";
        DBConnection.update (query);
        return true;
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
        } catch(SQLException ignored) {
            return true;    
        }
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
    	if (username.matches("\\s")) {
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
