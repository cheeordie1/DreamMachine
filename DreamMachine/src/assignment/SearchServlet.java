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
 * Servlet implementation class SearchServlet
 */
@WebServlet(description = "Servlet to handle searching", urlPatterns = { "/search" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String searchTerm;
		if (request.getParameter("term") == null)
			searchTerm = "";
		else
			searchTerm = request.getParameter("term").toString();
		request.setAttribute("term", searchTerm);
		String forward;
		if (request.getParameter("search-by") == null || request.getParameter("search-by").toString().equals("quiz")) {
			forward = "/content/search/quiz-search.jsp";
			List<Quiz> searches = new ArrayList<Quiz>();
			searches.addAll(Quiz.searchByName(searchTerm, true));
			if (request.getParameter("by-tag") != null)
				searches.addAll(Quiz.searchByTag(searchTerm, true));
			if (request.getParameter("by-creator") != null)
				searches.addAll(Quiz.searchByUsername(searchTerm, true));
			request.setAttribute("searchResults", searches);
		} else {
			forward = "/content/search/user-search.jsp";
			List<User> searches = new ArrayList<User>();
			if (request.getParameter("friends-only") != null) {
				if (request.getSession().getAttribute("loggedIn").toString().equals("false")) {
					response.sendRedirect("/DreamMachine/signup?notify=Make An Account to Search By Friends");
					return;
				}
				String username = request.getSession().getAttribute("username").toString();
				searches.addAll(User.searchByUsernameFriends(username, searchTerm, true));
			} else
				searches.addAll(User.searchByUsername(searchTerm, true));
			request.setAttribute("searchResults", searches);
		}
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
