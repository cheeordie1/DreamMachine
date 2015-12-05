<%@page import="assignment.Question"%>
<script src="DreamMachine/assets/javascripts/take-quiz.js"></script>
<%@ page import="java.util.*"%>
<%
	assignment.Quiz pageQuiz = (assignment.Quiz) request.getAttribute("quiz");
	List<assignment.Question> questions = Question.searchByQuizID(pageQuiz.quiz_id, true);
	for(int i = 0;i < questions.size(); i++) {
			String qUrl = "";
		if(questions.get(i).question_type.equals(assignment.Question.RESPONSE))
			qUrl = "/content/question/response-take.jsp";
		else if(questions.get(i).question_type.equals(assignment.Question.MATCHING))
			qUrl = "/content/question/matching-take.jsp";
		else
			qUrl = "/content/question/multichoice-take.jsp";
		%>
		
		<jsp:include page="<%= qUrl %>">
			<jsp:param name="idx" value="<%=i%>"/>
		</jsp:include>
	<%}%>
	<form method="post" action="DreamMachine/grade" name="grade-form" id="grade-form">
		<%
		  int count = 1;
		  for(assignment.Question q : questions) {%>
			<input type="hidden" id="q<%=count%>" name="q<%=count%>">
			<%count++;
		}%>
		<button type="submit" for="grade-form">Submit for Grading</button>
		<input type="button" name="quiz-id" value="<%=pageQuiz.quiz_id%>">
	</form>
	<script>new sQuiz("grade-form")</script>