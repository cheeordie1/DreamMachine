import java.sql.*;

public class User {
    
    /* constants */
    public final int MIN_PASSWORD_SIZE = 8;

    /* Instance variables */
    private String uid;
    public String name;
    public String password;
    public int quizzesTaken;
    public int quizzesMade;
    public ErrorMessages errorMessages;
    public User(String username, String password, String photo) {
        this.errorMessages = new ErrorMessages();
    }
    /* vars needed: quizes owned ~~~~~~ *** ~~~~~ */
    
    /* Attempts to save the given username and password combination. If the
     * two password attempts (password and retry) don't match, or any other
     * criteria for a valid password isn't met, the mapping is not created. */
    public boolean save(String username, String password, String retry) {
        boolean success = true;

        if(usernameUnavailable(username) || 
           usernameEmpty(username) ||
           usernameInvalid(username) || 
           passwordInsufficient(password) ||
           passwordMatchFailed(password, retry)) success = false;

        return success;
    }

    public boolean update() {
        return false;
    }   

    public boolean drop() {
        return true;
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
            "SELECT FROM " + DBInfo.USER_TABLE + " * WHERE username = " + username;
        ResultSet rs = DBConnection.executeQuery(query);
        
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
}
