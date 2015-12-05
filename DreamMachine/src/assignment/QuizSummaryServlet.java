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
 * Servlet implementation class QuizSummaryServlet
 */
@WebServlet(description = "Servlet to handle displaying quiz summaries", urlPatterns = { "/quiz/*" })
public class QuizSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizSummaryServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Quiz quiz = parseQuizFromURL(request.getRequestURL().toString());
		if (quiz == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		if ((boolean)request.getSession().getAttribute("loggedIn")) {
			List<User> users = User.searchByID(Integer.parseInt(request.getSession().getAttribute("uid").toString()));
			User curUser = users.get(0);
			users = User.searchByID(quiz.user_id);
			if (curUser.user_id == users.get(0).user_id)
				request.setAttribute("maker", "true");
		}
		request.setAttribute("quiz_id", quiz.quiz_id);
		String forward = "/content/quiz/quiz-summary.jsp";
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
	 * parses the quiz trying to be requested from the URL
	 */
	private Quiz parseQuizFromURL(String url) {
		String match = "/quiz/";
		int idx = url.indexOf(match);
		int quiz_id;
		try {
			quiz_id = Integer.parseInt(url.substring(idx + match.length()));
		} catch (NumberFormatException nfe) {
			return null;
		}
		List<Quiz> pageQuiz = Quiz.searchByID(quiz_id);
		if (pageQuiz.isEmpty())
			return null;
		else
			return pageQuiz.get(0);
	}
}
