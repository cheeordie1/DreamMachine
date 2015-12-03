<%@page import = "assignment.DBConnection"%>
<%@page import = "assignment.Quiz"%>
<%@page import = "assignment.User"%>
<%@page import = "assignment.QuizStatistics"%>


<%@page import = "java.util.ArrayList"%>
<%@page import = "java.text.NumberFormat" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	int quiz_id = Integer.parseInt(request.getParameter("quiz_id")); 
	Quiz quiz = new Quiz(DBConnection.query("SELECT * FROM quizzes WHERE quiz_id =" + quiz_id));
	User creator = new User(DBConnection.query("SELECT * FROM users WHERE user_id =" + quiz.user_id));
	String creatorName = creator.username; 
	int user_id = Integer.parseInt(request.getParameter("user_id"));
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=quiz.name%></title>
</head>

<body>
	<!-- Link to user page  -->
	<h1>Created by <a href=#><%=creatorName%></a></h1>
	<br><br>
	<div> 
		<h2>Description:</h2>
		<p> <%=quiz.description%> </p>
	</div>
	
	<div> 
	
	
		<div> 
			<h1>Your Past Performance</h1>
			
			
		</div>
	
	
	
	</div>
	
	
	<div> <a href=#>Take Quiz</a></div>
	
	
	
	
	
	
	
	

</body>
</html>