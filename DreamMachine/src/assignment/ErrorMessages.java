package assignment;

import java.util.*;

public class ErrorMessages {
    public Map<String, List<String>> errors;

    public ErrorMessages() {
        this.errors = new HashMap<String, List<String>>();
    }
    
    public void addError(String key, String message) {
        List<String> errorMessages;
        errorMessages = errors.get(key);
        if (errorMessages == null) {
        	errorMessages = new ArrayList<String>();
        	errors.put(key, errorMessages);
        }
    	errorMessages.add(message);
    }
    
    public List<String> getErrors(String key) {
    	List<String> retList = errors.get(key);
    	if (retList == null) retList = new ArrayList<String>();
    	return retList;
    }
}
