package assignment;

import java.util.*;

public class ErrorMessages {
    public Map<String, List<String>> errors;

    public ErrorMessages() {
        this.errors = new HashMap<String, List<String>>();
    }
    
    /**
     * Add an error message to the hash of string keys to
     * error messages. 
     * @param key to identify types of errors.
     * @param message error to add to the list of errors.
     */
    public void addError(String key, String message) {
        List<String> errorMessages;
        errorMessages = errors.get(key);
        if (errorMessages == null) {
        	errorMessages = new ArrayList<String>();
        	errors.put(key, errorMessages);
        }
    	errorMessages.add(message);
    }
    
    /**
     * Get a list of error messages associated with a given
     * error type.
     * @param key to search for a list of errors by
     * @return List of errors associated with error key.
     */
    public List<String> getErrors(String key) {
    	List<String> retList = errors.get(key);
    	if (retList == null) retList = new ArrayList<String>();
    	return retList;
    }
}
