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
 * Servlet implementation class QuestionCreateServlet
 */
@WebServlet(description = "Servlet to handle creating questions", urlPatterns = { "/question-create" })
public class QuestionCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionCreateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("loggedIn").equals("false")) {
			response.sendRedirect("/DreamMachine/login?notify=You Must Login to Create a Quiz");
			return;
		}
		int uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
		if (request.getParameter("quiz-id") == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		int qid = Integer.parseInt(request.getParameter("quiz-id"));
		List<Quiz> quizzes = Quiz.searchByID(qid);
		if (quizzes.isEmpty()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		Quiz quiz = quizzes.get(0);
		if (quiz.user_id != uid) {
			response.sendRedirect("/DreamMachine/home");
			return;
		}
		List<Question> questions = Question.searchByUserID(uid);
		String forward = "/content/question/question-create.jsp";
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
