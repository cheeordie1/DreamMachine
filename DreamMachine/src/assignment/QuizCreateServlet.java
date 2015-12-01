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
		if (request.getSession().getAttribute("loggedIn").toString().equals("false")) {
			response.sendRedirect("/DreamMachine/signup?notify=Create an Account in Order to Make Quizzes!");
			return;
		}
		Quiz quiz = new Quiz();
		quiz.user_id = Integer.parseInt(request.getSession().getAttribute("uid").toString());
		if (request.getParameter("quiz-name") != null)
		    quiz.name = request.getParameter("quiz-name").toString();
		if (request.getParameter("quiz-description") != null)
			quiz.description = request.getParameter("quiz-description").toString();
		quiz.single_page = request.getParameter("single-page").toString().equals("single");
		if (!quiz.single_page)
			quiz.immediate_correct = request.getParameter("immediate-correct") != null;
		quiz.random_questions = request.getParameter("random-questions").toString().equals("random");
		quiz.practice_mode = request.getParameter("practice-mode")!= null;
		if (quiz.save()) {
			int numTags = Integer.parseInt(request.getParameter("num-tags").toString());
			System.out.println(numTags);
			for (int curTag = 1; curTag <= numTags; curTag++) {
				Tag tag = new Tag();
				tag.quiz_id = quiz.quiz_id;
				tag.tag = request.getParameter("tag" + curTag).toString();
				tag.save();
			}
			response.sendRedirect("/DreamMachine/question-create?quiz-id=" + quiz.quiz_id);
		} else {
			request.getSession().setAttribute("errors", quiz.errors);
			response.sendRedirect("/DreamMachine/quiz-create");
		}
	}

}
