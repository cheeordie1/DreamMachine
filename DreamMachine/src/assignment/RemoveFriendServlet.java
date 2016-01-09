package assignment;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RemoveFriendServlet
 */
@WebServlet(description = "Servlet to handle removal of friendships", urlPatterns = { "/remove-friend" })
public class RemoveFriendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFriendServlet() {
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
		String pageURL = request.getParameter("pageURL");
		List<Friend> friendList = Friend.searchByUserIDUserID(sender, receiver);
		if (!friendList.isEmpty()) {
		    Friend friendship = friendList.get(0);
		    friendship.delete();
		}
		response.sendRedirect(pageURL);
	}

}
