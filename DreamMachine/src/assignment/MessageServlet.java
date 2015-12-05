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
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String uname = (String) request.getSession().getAttribute("username");
		HashMap<String, ArrayList<String>> allMessages = Message.retreiveMessages(uname);
		
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
		
		Message.sendMessage(sender, destination, message);
	}

}
