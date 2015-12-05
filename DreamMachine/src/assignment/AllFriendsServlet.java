package assignment;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import assignment.User;
import java.util.*;
/**
 * Servlet implementation class AllFriendsServlet
 */
@WebServlet(urlPatterns = {"/friends/*"})
public class AllFriendsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AllFriendsServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		String match = "/friends/";
		int idx = url.indexOf(match);
		String name = url.substring(idx + match.length());

		User user = User.searchByUsername(name).get(0);
		List<Integer> friends = Friend.getFriends(user.user_id);
		ArrayList<String> friendNames = new ArrayList<String>();
		for(int i = 0; i < friends.size(); i++) {
			User f = User.searchByID(friends.get(i)).get(0);
			friendNames.add(f.username);
		}

		String thisUser = (String) request.getSession().getAttribute("username");
		request.setAttribute("user", thisUser);
		request.setAttribute("friends", friendNames);
		String forward = "/content/friends/all-friends.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
}
