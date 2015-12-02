package assignment;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet(description = "Servlet to handle account display", urlPatterns = { "/user/*" })
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getRequestURL().toString();
		User userRequested = parseUserFromURL(url);
		if (userRequested == null) {
			response.sendError(404);
			return;
		}
		
		request.setAttribute("pageUser", userRequested.username);
		String forward;
		if(userRequested.username.equals(request.getSession().getAttribute("username")))
			forward = "/content/account/account.jsp";	
		else forward = "/content/account/account-visit.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * parses the usename trying to be requested from the URL
	 */
	private User parseUserFromURL(String url) {
		String match = "/user/";
		int idx = url.indexOf(match);
		String name = url.substring(idx + match.length());
		List<User> pageUser = User.searchByUsername(name);
		if (pageUser.isEmpty())
			return null;
		else
			return pageUser.get(0);
	}
	
}
