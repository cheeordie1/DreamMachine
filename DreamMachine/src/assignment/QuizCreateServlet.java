package assignment;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuizCreateServlet
 */
@WebServlet(description = "Servlet to handle quiz creation", urlPatterns = { "/quiz-create" })
public class QuizCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizCreateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("loggedIn").toString().equals("false")) {
			response.sendRedirect("/DreamMachine/signup?notify=Create an Account in Order to Make Quizzes!");
			return;
		}
		String forward = "/content/quiz/quiz-create.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
		request.getSession().removeAttribute("errors");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
