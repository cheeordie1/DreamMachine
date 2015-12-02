package assignment;

import java.sql.ResultSet;
import java.util.*;

public class Matching extends Answer {
	// instance variables
	List<String> leftOptions;
	List<String> rightOptions;
	Map<String, String> matches;

	public Matching() {
		super();
		leftOptions = new ArrayList<String>();
		rightOptions = new ArrayList<String>();
		matches = new HashMap<String, String>();
	}
	
	public Matching(ResultSet rs) {
		super(rs);
		leftOptions = new ArrayList<String>();
		rightOptions = new ArrayList<String>();
		matches = new HashMap<String, String>();
		parseOptions(answer);
	}
	
	public void addAnswer(String left, String right) {
		leftOptions.add(left);
		rightOptions.add(right);
		matches.put(left, right);
	}
	
	public void deleteAnswer(String answer) {
		String[] pair = answer.split("=");
		leftOptions.remove(pair[0]);
		rightOptions.remove(pair[1]);
		matches.remove(pair[0]);
	}
	
	private void parseOptions(String options) {
		// example of options
		// left side <===matches===> right side
		// duck=ball,goose=red,ribbon=fly
		String[] matchesString = options.split(SEPARATOR);
		for (String match : matchesString) {
			String[] pair = match.split(DELIM);
			addAnswer(pair[0], pair[1]);
		}
	}

	public boolean checkAnswer (String userInput) {
		//user input has to have "="
		//example of user input: "milk=sausage"	
		String[] splitUserInput = userInput.split(DELIM);
		if (!matches.get(splitUserInput[0]).equals(splitUserInput[1])) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (String match : matches.keySet()) {
			if (result.toString().length() > 0)
				result.append(SEPARATOR);
			result.append(match + DELIM + matches.get(match));
		}
		return result.toString();
	}
}
