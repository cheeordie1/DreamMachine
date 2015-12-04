package assignment;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(description = "Servlet for loading Home page", urlPatterns = { "/home" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO move top of client-chat here
		boolean loggedIn = (boolean) request.getSession().getAttribute("loggedIn");
		if(loggedIn) {
			
			int user_id = (Integer) request.getSession().getAttribute("uid");
			ArrayList<Integer> pendingRequests = Friend.getFriendRequests(user_id);
			request.setAttribute("friendRequests", pendingRequests);
			String username = (String) request.getSession().getAttribute("username");
			
			/* get message history from db */
			HashMap<String, ArrayList<String>> messages = Message.retreiveMessages(username);
			request.getSession().setAttribute("messages", messages);
			request.setAttribute("numMessages", messages.size());
			
			/* get relevant friend information */
			List<Integer> allFriends = Friend.getFriends(user_id);
			
			/* blocking data */
			ArrayList<Integer> blockedUsers = Friend.getBlockedFriends(user_id);
			ArrayList<Integer> blockingUsers = Friend.getBlockingFriends(user_id);
			request.getSession().setAttribute("blockedUsers", blockedUsers);
			request.getSession().setAttribute("blockingUsers", blockingUsers);
			
			request.setAttribute("friends", allFriends);
		}
		String forward = "/content/home/home.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	private HashSet<Integer> getOnlineFriends(HttpServletRequest request, List<Integer> allFriends) {
		HashSet<Integer> onlineFriends = new HashSet<Integer>();
		HashSet<Integer> onlineUsers = 
			(HashSet<Integer>) request.getServletContext().getAttribute("onlineUsers");
		for(int id : allFriends) {
			if(onlineUsers.contains(id))
				onlineFriends.add(id);
		}
		return onlineFriends;
	}
}
