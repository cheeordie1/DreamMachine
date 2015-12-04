package assignment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuizStats {

    public static int quizzesTaken(int uid) {
    	System.out.println("hi");
    	ResultSet quizData = DBConnection.query("SELECT COUNT(*) FROM scores WHERE user_id=" +uid);
    	int quizzesTaken = 0;
    	try {
    		quizzesTaken = quizData.getInt(1);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return quizzesTaken;
    }
    
    public static int quizzesMade(int uid) {
    	ResultSet quizData = DBConnection.query("SELECT COUNT(*) FROM quizzes WHERE user_id=" +uid);
    	int quizzesMade = 0;
    	try {
    		quizzesMade = quizData.getInt(1);
    	} catch (SQLException e) {
    		e.printStackTrace();
    	}
    	return quizzesMade;
    }
    
   /* public static boolean isModerator(int uid) {
    	User user = User.searchByID(uid).get(0);
    	return user.isModerator;
    } */
    
    public static int numFriends(int uid) {
    	return Friend.searchByUserID(uid).size();
    }
    
    public static int perfQuizzes(int uid) {
    	//String queryStr = "SELECT * FROM scores WHERE user_id=" +uid+ " AND"
    	return 0;
    }
}
