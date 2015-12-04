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
