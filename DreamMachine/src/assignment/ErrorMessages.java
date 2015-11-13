package assignment;

import java.util.*;

public class ErrorMessages {
   
    /* error messages */
    public static final String USERNAME_UNAVAILABLE = 
        "That username is unavailable. Please try another.";
    public static final String USERNAME_EMPTY = 
        "Please enter a username.";
    public static final String USERNAME_INVALID = 
        "Please limit your username to only characters and numbers.";
    public static final String PASSWORD_INSUFFICIENT = 
        "Please choose a password at least 8 characters in length.";
    public static final String PASSWORD_EMPTY = 
        "Please provide a password at least 8 characters long";
    public static final String PASSWORD_MATCH_FAILED =
        "The two password entries don't match. Please try again.";

    public Map<String, ArrayList<String>> errors;

    public ErrorMessages() {
        this.errors = new HashMap<String, ArrayList<String>>();
    }
    
    public void addError(String uid, String message) {
        ArrayList<String> errorMessages = this.errors.get(uid);
        if(errorMessages == null)
            errorMessages = new ArrayList<String>();
        errorMessages.add(message);

        /* TODO: do I need to put it again? or will it work without? */
        this.errors.put(uid, errorMessages);
    }   
}
