package assignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "Servlet to handle log in pages", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("loggedIn").toString().equals("true")) {
			response.sendRedirect("/DreamMachine/home");
		} else {
			String forward = "/content/login/login.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
			request.getSession().removeAttribute("errors");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		ErrorMessages errors = new ErrorMessages();
		request.getSession().setAttribute("errors", errors);
		if (username == null || username.isEmpty() ||
			password == null || password.isEmpty()) {
			errors.addError(User.LOGIN_ERROR, User.LOGIN_EMPTY);
			response.sendRedirect("/DreamMachine/login");
			return;
		}
		List<User> users = User.searchByUsername(username, false);
		if (users.isEmpty()) {
			errors.addError(User.LOGIN_ERROR, User.LOGIN_FAILED);
		} else {
			User user = users.get(0);
			if (user.checkPassword(password)) {
				/* message cache will originally be empty */
				HashMap<String, ArrayList<String>> messageCache
					= Message.retreiveMessages(user.username);
				
				/* friend requests */
				ArrayList<Integer> friendRequests = Friend.getAllPendingRequests(user.user_id);
				request.getSession().setAttribute("friendRequests", friendRequests);

				/* set up session information we must populate the friends caches here */
				List<Integer> friendsCache = Friend.getFriends(user.user_id);
				List<Integer> blockCache = Friend.getBlockedFriends(user.user_id);
				List<Integer> rejectCache = new ArrayList<Integer>();
				
				request.getSession().setAttribute("rejectedFriends", rejectCache);
				request.getSession().setAttribute("blockedFriends", blockCache);
				request.getSession().setAttribute("friends", friendsCache);
				request.getSession().setAttribute("cache", messageCache);
				request.getSession().setAttribute("loggedIn", "true");
				request.getSession().setAttribute("username", user.username);
				request.getSession().setAttribute("uid", user.user_id);
				response.sendRedirect("/DreamMachine/home");
				return;
			} else {
				errors.addError(User.LOGIN_ERROR, User.LOGIN_FAILED);
			}
		}
		response.sendRedirect("/DreamMachine/login");
	}

}
