<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/quiz-summary.css">
<%@ page import="java.util.List,assignment.*" %>
<%
  List<Quiz> quizzes = Quiz.searchByID(Integer.parseInt(request.getAttribute("quiz_id").toString()));
  Quiz quiz = quizzes.get(0);
%>
<title><%= quiz.name %></title>
</head>
<body>
  <div id="quiz-summary-container">
  </div>
  <form name="take-quiz" action="TakeQuizServlet" method="get">
    <input type="button" name="take-quiz-button" value="takeQuiz" 
    onClick="return callServlet()">
  </form>
</body>
</html>