package assignment;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet(description = "Servlet to handle signing up for an account", urlPatterns = { "/signup" })
public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignupServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("loggedIn").toString().equals("true")) {
			response.sendRedirect("http://localhost:8080/DreamMachine/home");
		} else {
			String forward = "content/signup/signup.jsp";
			RequestDispatcher rd = request.getRequestDispatcher(forward);
			rd.forward(request, response);
			request.getSession().removeAttribute("errors");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = new User((String) request.getParameter("username"));
		user.setPassword(request.getParameter("password"));
		user.repassword = request.getParameter("re-password");
		user.photoPart = request.getPart("photo");
		if (!user.save()) {
			request.getSession().setAttribute("errors", user.errorMessages);
			response.sendRedirect("http://localhost:8080/DreamMachine/signup");
		} else {
			request.getSession().setAttribute("loggedIn", "true");
			request.getSession().setAttribute("username", user.username);
			request.getSession().setAttribute("uid", user.id);
			response.sendRedirect("http://localhost:8080/DreamMachine/home");
		}
	}

}
