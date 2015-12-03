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
 * Servlet implementation class QuizTakeServlet
 */
@WebServlet(description = "Servlet to handle taking quiz", urlPatterns = { "/quiz-take/*" })
public class QuizTakeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizTakeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (request.getParameter("quiz-id") == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		int qid = Integer.parseInt(request.getParameter("quiz-id"));
		
		Quiz quiz = Quiz.searchByID(qid).get(0);
		String forward;
		if (quiz.single_page) {
			forward = "/content/quiz/single-page-quiz.jsp";
		} else {
			forward = "/content/quiz/multi-page-quiz.jsp";
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
