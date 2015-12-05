package assignment;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuestionCreateServlet
 */
@WebServlet(description = "Servlet to handle creating questions", urlPatterns = { "/question-create" })
@MultipartConfig
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
		int uid = -1;
		if (request.getSession().getAttribute("loggedIn").toString().equals("false")) {
			response.sendRedirect("/DreamMachine/login?notify=You Must Login to Create a Quiz");
			return;
		} else {
			try {
				uid = Integer.parseInt(request.getSession().getAttribute("uid").toString());
			} catch (NumberFormatException nfe) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		}
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
		int question_id = -1;
		if (request.getParameter("question-id") != null) {
			try {
				question_id = Integer.parseInt(request.getParameter("question-id").toString());
				request.setAttribute("question_id", question_id);
			} catch (NumberFormatException nfe) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}
		}
		if (request.getParameter("question-type") != null) {
			request.setAttribute("question-type", request.getParameter("type"));
		} else request.setAttribute("question-type", Question.RESPONSE);
		request.setAttribute("pageQuiz", quiz);
		String forward = "/content/question/question-create.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		rd.forward(request, response);
		request.getSession().removeAttribute("errors");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Question question = new Question();
		String questionType = request.getParameter("question-type").toString();
		question.photoPart = request.getPart("photo");
		int quiz_id = Integer.parseInt(request.getParameter("quiz-id").toString());
		if (questionType.equals(Question.RESPONSE)) {
			question.question_type = Question.Type.RESPONSE;
			Response responseQuestion = new Response();
			responseQuestion.subset = request.getParameter("subset").toString();
			responseQuestion.ordered = request.getParameter("order") != null;
			int numAnswers = Integer.parseInt(request.getParameter("num-answers").toString());
			for (int curAnswer = 0; curAnswer < numAnswers; curAnswer++) {
				String answer = request.getParameter("answer" + curAnswer);
				responseQuestion.addAnswer(answer);
			}
			
			if(!responseQuestion.save()) {
				request.getSession().setAttribute("errors", responseQuestion.errorMessages);
				response.sendRedirect("/DreamMachine/question-create?questionType=" + questionType + "&quiz-id=" + quiz_id);
			}
		} else if (questionType.equals(Question.MATCHING)) {
			question.question_type = Question.Type.MATCHING;
			
		} else if (questionType.equals(Question.MULTICHOICE)) {
			question.question_type = Question.Type.MULTICHOICE;
			
		} else {
			response.sendError(HttpServletResponse.SC_CONFLICT);
			return;
		}
	}

}
