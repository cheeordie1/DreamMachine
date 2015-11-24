package assignment;

import java.sql.*;
import java.util.Random;
import java.security.*;

public class User {
    
    /* constants */
    private final int MIN_PASSWORD_SIZE = 8;
    private final int SALT_SIZE = 2;

    /* instance variables */
    public int uid;
    public String password;
    public String retry;
    public int quizzesTaken;
    public int quizzesMade;
    public ErrorMessages errorMessages;

    /* info for database entry */
    public String username;
    public String passwordDigest; 
    public String salt;
    
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
        if(usernameUnavailable(username) || 
            usernameEmpty(username) ||
            usernameInvalid(username) || 
            passwordInsufficient(password) ||
            passwordMatchFailed(password, retry)) success = false;
        else {
            /* create a new entry for this username password pair */
            String entry =
                "INSERT INTO " + DBInfo.USER_TABLE + "(username,salt,digest) VALUES (" +
                    "\"" + this.username + "\"," +
                    "\"" + this.salt + "\"," +
                    "\"" + this.passwordDigest + "\"" +
                ")";
            this.uid = DBConnection.insertUpdate(entry);
            System.out.println(this.uid);
        }

        return success;
    }

    public boolean update() {
        return false;
    }   

    public boolean destroy() {
        return DBConnection.deleteUpdate(DBInfo.USER_TABLE, this.uid);
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
            MessageDigest md = MessageDigest.getInstance("SHA");
            password += this.salt;
            this.passwordDigest = hexToString(md.digest(password.getBytes()));
        } catch (NoSuchAlgorithmException ignored) {}
    }

    /* ensures the username is not null or empty. If the given username is null
     * or empty, this returns true. */
    private boolean usernameEmpty(String str) {
        return str == null || str.length() == 0;
    }
    
    /* ensures the username is not already in use. If the given username is
     * already in use, this returns true. */
    private boolean usernameUnavailable(String username) {
        /* Query the database for the given username. If there, return false */ 
        String query = 
            "SELECT * FROM " + DBInfo.USER_TABLE + " WHERE username = " + username;
        ResultSet rs = DBConnection.query(query);
        
        /* if result set isn't empty, this username can't be used. */
        try {
            if(rs.next()) {
                this.errorMessages.addError(uid, ErrorMessages.USERNAME_UNAVAILABLE);
                return false;
            }
        } catch(SQLException ignored) {
            return false;    
        }
        return true;
    }
    
    /* ensure password is greater than 8 characters. If the username is fewer
     * than 8 characters, this returns true. */
    private boolean passwordInsufficient(String password) {
        if(password.length() < MIN_PASSWORD_SIZE) {
            this.errorMessages.addError(uid, ErrorMessages.PASSWORD_INSUFFICIENT);
            return false;
        }
        return true;
    }
    
    /* ensures the username is valid. If the username is invalide (there are
     * any symbols or spaces) this returns true */
    private boolean usernameInvalid(String username) {
        this.errorMessages.addError(uid, ErrorMessages.USERNAME_INVALID);
        return true;
    }
    
    /* ensure that the user entered the passwrod correctly the second time for
     * added unsurance. This returns true is the passwords don't match. */
    private boolean passwordMatchFailed(String password, String repeat) {
        if(!password.equals(repeat)) {
            this.errorMessages.addError(uid, ErrorMessages.PASSWORD_MATCH_FAILED);
            return false;
        }
        return true;
    }

    private boolean passwordInvalid() {
        return true;
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
