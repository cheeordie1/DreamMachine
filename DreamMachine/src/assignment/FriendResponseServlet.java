package assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FriendResponse
 */
@WebServlet(urlPatterns = { "/friendResponse/*" })
public class FriendResponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendResponseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* error checking on request sender */
		
		String url = request.getRequestURL().toString();
		User sender = parseSenderFromURL(url);
		request.setAttribute("sender", sender.username);
		String forward = "/content/friends/friends-response.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String senderString, receiverString;
		User sender, receiver;
		
		/* get ourselves */
		receiverString = (String) request.getSession().getAttribute("username");
		receiver = User.searchByUsername(receiverString).get(0);
		
		/* get the User who sent the request */
		senderString = request.getParameter("sender");
		sender = User.searchByUsername(senderString).get(0); 
		
		if(request.getParameter("accept") != null) {
			/* add this use to the friendsCache and update the db */
			ArrayList<Integer> friendsCache = (ArrayList<Integer>) request.getSession().getAttribute("friends");
			friendsCache.add(sender.id);
			Friend.updateFriendRequest(sender.id, receiver.id, Friend.ACCEPTED);
			request.getSession().setAttribute("friends", friendsCache); // TODO is this yes?
		} 
		else if(request.getParameter("reject") != null) {
			ArrayList<Integer> rejectCache = (ArrayList<Integer>) request.getSession().getAttribute("friends");
			rejectCache.add(sender.id);
			Friend.updateFriendRequest(sender.id, receiver.id, Friend.DECLINED);
			request.getSession().setAttribute("friends", rejectCache); // TODO is this yes?
		}
		else if(request.getParameter("block") != null) {
			ArrayList<Integer> friendsCache = (ArrayList<Integer>) request.getSession().getAttribute("friends");
			friendsCache.add(sender.id);
			Friend.updateFriendRequest(sender.id, receiver.id, Friend.BLOCKED);
			request.getSession().setAttribute("friends", friendsCache); // TODO is this yes?
		}
		
		/* redirect back to home */
		response.sendRedirect("/DreamMachine/home");

	}
	
	private User parseSenderFromURL(String url) {
		String match = "/friendResponse/";
		int idx = url.indexOf(match);
		String name = url.substring(idx + match.length());
		List<User> pageUser = User.searchByUsername(name);
		if (pageUser.isEmpty())
			return null;
		else
			return pageUser.get(0);
	}

}
