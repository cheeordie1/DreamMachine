package assignment;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ArrayList;
/**
 * Servlet implementation class MessageServlet
 */
@WebServlet( urlPatterns = { "/MessageServlet", "/message"})
public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		HashMap<String, ArrayList<String>> allMessages = 
			(HashMap<String, ArrayList<String>>) request.getSession().getAttribute("messages");
		
		String friend = request.getParameter("message");
		ArrayList<String> messages = allMessages.get(friend);
		if(messages == null) return; 
		if(messages.isEmpty()) return;
		
		String jsonString = "[";
		for(String message : messages) 
			jsonString += "{\"message\" : \"" + message +"\"},";
		jsonString = jsonString.substring(0,jsonString.length()-1);
		jsonString += "]";
		
		out.print(jsonString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sender = (String) request.getSession().getAttribute("username");
		String message = request.getParameter("message");
		String destination = request.getParameter("username");
		
		int destinationId = User.searchByUsername(destination).get(0).id;
		Message.sendMessage(sender, destination, message);
		
		/* update the message cache */
		HashMap<String, ArrayList<String>> allMessages = 
				(HashMap<String, ArrayList<String>>) request.getSession().getAttribute("messages");
		ArrayList<String> senderList = allMessages.get(destination);
		if(senderList == null) senderList = new ArrayList<String>();
		ArrayList<String> destinationList = allMessages.get(destination);
		if(destinationList == null) destinationList = new ArrayList<String>();
		
		senderList.add(message);
		destinationList.add(message);
		allMessages.put(sender, senderList);
		allMessages.put(destination, destinationList);
		
		request.getSession().setAttribute("messages", allMessages);
	}

}
