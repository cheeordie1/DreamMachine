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
@WebServlet(description = "Servlet to handle searching", urlPatterns = { "/search", "/search/*", "/SearchServlet" })
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
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String text = request.getParameter("term");
		List<User> list = User.searchBySubstring(text);
		
		/* decide who to inlude in the list based on block list */
		ArrayList<Integer> blockedList = (ArrayList<Integer>) request.getSession().getAttribute("blockCache");
		if(blockedList == null) blockedList = new ArrayList<Integer>();
		ArrayList<String> result = new ArrayList<String>();
		for(User u : list) {
			if(!blockedList.contains(u.user_id))
				result.add(u.username);
		}
		request.setAttribute("searchUserResults", result);
		
		/* quiz searches */
		List<Quiz> quizzes = Quiz.searchBySubstring(text);
		request.setAttribute("searchQuizResults", quizzes);
		
		String forward = "/content/search/search.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

}
