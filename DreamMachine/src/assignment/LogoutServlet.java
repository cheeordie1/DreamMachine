package assignment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet(description = "Servlet to handle logging out", urlPatterns = { "/logout" })
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogoutServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if ((boolean)request.getSession().getAttribute("loggedIn")) {
			/* remove this user from online listing */
			HashSet<Integer> onlineUsers = 
				(HashSet<Integer>) request.getServletContext().getAttribute("onlineUsers");
			int uid = (Integer) request.getSession().getAttribute("uid");
			onlineUsers.remove(new Integer(uid));
			request.getServletContext().setAttribute("onlineUsers", onlineUsers);
			
			/* remove session information for this user */
			request.getSession().removeAttribute("rejectedFriends");
			request.getSession().removeAttribute("requests");
			request.getSession().removeAttribute("blockedFriends");
			request.getSession().removeAttribute("friends");
			request.getSession().removeAttribute("cache");
			request.getSession().removeAttribute("loggedIn");
			request.getSession().removeAttribute("username");
			request.getSession().removeAttribute("uid");
		}
		response.sendRedirect("/DreamMachine/home");
	}

}
