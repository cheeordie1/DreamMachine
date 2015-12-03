package assignment;

import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {

	private static final String TABLE_NAME = "messages";
	
	public static void sendMessage(int sender, int receiver, String message) {
		String entry = "INSERT INTO " + TABLE_NAME + " VALUES ('" +
			sender + "', '" + receiver + "', '" + message + "')";
		DBConnection.update(entry);
	}
	
	public static HashMap<Integer, ArrayList<String>> retreiveMessages(int uid) {
		HashMap<Integer, ArrayList<String>> map 
			= new HashMap<Integer, ArrayList<String>>();
		
		String query = "SELECT * FROM " + TABLE_NAME + 
			" WHERE receiver = " + uid;
		ResultSet rs = DBConnection.query(query);
		try {
			while(rs.next()) {
				int sender = rs.getInt("sender");
				ArrayList<String> messages = (map.containsKey(sender) ? 
					map.get(sender) : new ArrayList<String>());
				messages.add(rs.getString("message"));
				map.put(sender, messages);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
}
