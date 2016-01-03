package assignment;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddFriendServlet
 */
@WebServlet(description = "Servlet to handle sending and accepting friend requests", urlPatterns = { "/add-friend" })
public class AddFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFriendServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int sender = Integer.parseInt(request.getParameter("sender"));
		int receiver = Integer.parseInt(request.getParameter("receiver"));
		List<Friend> friendList = Friend.searchByUserIDUserID(sender, receiver);
		if (friendList.isEmpty()) {
			Friend friendRequest = new Friend();
			friendRequest.sender = sender;
			friendRequest.receiver = receiver;
			friendRequest.status = Friend.PENDING;
			friendRequest.save();
		} else {
		    Friend friendship = friendList.get(0);
		    if (sender == friendship.receiver) {
		    	if (friendship.status == Friend.PENDING) {
		    		friendship.status = Friend.ACCEPTED;
		  	  	}
		    }
		}
		response.sendRedirect("/DreamMachine/user/");
	}

}
