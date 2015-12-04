package assignment;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {

	private static final String TABLE_NAME = "messages";
	
	public static void sendMessage(String sender, String receiver, String message) {
		String entry = "INSERT INTO " + TABLE_NAME + " VALUES ('" +
			sender + "', '" + receiver + "', '" + message + "')";
		DBConnection.update(entry);
	}
	
	public static HashMap<String, ArrayList<String>> retreiveMessages(String username) {
		HashMap<String, ArrayList<String>> map 
			= new HashMap<String, ArrayList<String>>();
		
		String query = "SELECT * FROM " + TABLE_NAME + 
			" WHERE sender = '" + username + 
			"' OR receiver = '" + username + "'";
		ResultSet rs = DBConnection.query(query);
		try {
			while(rs.next()) {
				ArrayList<String> senderList = map.get(rs.getString("sender"));
				ArrayList<String> receiverList = map.get(rs.getString("receiver"));
				if(senderList == null) senderList = new ArrayList<String>();
				if(receiverList == null) receiverList = new ArrayList<String>();
				senderList.add(rs.getString("message"));
				receiverList.add(rs.getString("message"));
				map.put(rs.getString("sender"), senderList);
				map.put(rs.getString("receiver"), receiverList);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
}
