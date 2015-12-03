<%@page import = "assignment.DBConnection"%>
<%@page import = "assignment.Quiz"%>
<%@page import = "assignment.User"%>
<%@page import = "assignment.QuizStats"%>
<%@page import = "assignment.Score"%>
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
	QuizStats stats = new QuizStats(quiz_id);
	ArrayList <Score> userScores = Score.pastPerformances(quiz_id, user_id);
	ArrayList <Score> bestScores = Score.highestPerformers(quiz_id);
	ArrayList <Score> topDailyScores = Score.highestPerformersPastDay(quiz_id);
	ArrayList <Score> recentScores = Score.recentPerformances(quiz_id);
%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=quiz.name%></title>
</head>

<body>
	<!-- Link to user page  -->
	<div id="creator-page-link">
		<h2>Created by <a href=#><%=creatorName%></a></h2>
	</div>
	
	<br><br>
	
	<div id="description"> 
		<h2>Description:</h2>
		<p><%=quiz.description%></p>
	</div>
	
	<br><br>
	
	<div id="stats"> 
			<h3>Quiz Statistics</h3>
			<div id="pop-quizzes-cont" class="well">
				<table class="table table-condensed">
				  	<tr>
				  		<th>Date</th> 
				  		<th>Times Played</th>
				  		<th>Average Score</th>
				  		<th>High Score</th>
				  		<th>Low Score</th>
				  	</tr>
				     	<tr>
				        	<td> <%=stats.date%></td>
				      	</tr>
				      	<tr>
				        	<td> <%=stats.timesPlayed%> </td>
				      	</tr>
				      	<tr>
				        	<td> <%=stats.averageScore%></td>
				      	</tr>
				      	<tr>
				        	<td> <%=stats.highScore%></td>
				      	</tr>
				      	<tr>
				        	<td> <%=stats.lowestScore%></td>
				      	</tr>
				</table>
			</div>
		</div>
		
	
	<div id="stats"> 
		<h2>Quiz Statistics:</h2>
		<h3>Date: </h3>
	</div>
	
	<br><br>
	
	
	<div> 
		<div id="past-performanes"> 
			<h3>Your Past Performance</h3>
			<div id="pop-quizzes-cont" class="well">
				<table class="table table-condensed">
				  	<tr>
				  		<th>Date</th> 
				  		<th>User</th>
				  		<th>Score</th>
				  		<th>Time</th>
				  	</tr>
				    <%for (Score score : userScores) 
				    { %>
				     	<tr>
				        	<td> <%=score.whenPlayed%></td>
				      	</tr>
				      	<tr>
				        	<td> <a href=#> <%=score.user.username%> </a></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.score%></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.time%></td>
				      	</tr>
				      	
				     <%} %>
				</table>
			</div>
		</div>
		
		<div id="best-performanes"> 
			<h3>Highest Scores</h3>
			<div id="pop-quizzes-cont" class="well">
				  <table class="table table-condensed">
				  	<tr>
				  		<th>Date</th> 
				  		<th>User</th>
				  		<th>Score</th>
				  		<th>Time</th>
				  	</tr>
				    <%for (Score score : bestScores) 
				    { %>
				     	<tr>
				        	<td> <%=score.whenPlayed%></td>
				      	</tr>
				      	<tr>
				        	<td> <a href=#> <%=score.user.username%> </a></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.score%></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.time%></td>
				      	</tr>
				      	
				     <%} %>
				  </table>
			</div>
		</div>
		
		<div id="top-daily-performanes"> 
			<h3>Top Daily Performance</h3>
			<div id="pop-quizzes-cont" class="well">
				  <table class="table table-condensed">
				  	<tr>
				  		<th>Date</th> 
				  		<th>User</th>
				  		<th>Score</th>
				  		<th>Time</th>
				  	</tr>
				    <%for (Score score : topDailyScores) 
				    { %>
				     	<tr>
				        	<td> <%=score.whenPlayed%></td>
				      	</tr>
				      	<tr>
				        	<td> <a href=#> <%=score.user.username%> </a></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.score%></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.time%></td>
				      	</tr>
				      	
				     <%} %>
				  </table>
			</div>
		</div>
		
		<div id="recent-performanes"> 
			<h3>Most Recent Performance</h3>
			<div id="pop-quizzes-cont" class="well">
				  <table class="table table-condensed">
				  	<tr>
				  		<th>Date</th> 
				  		<th>User</th>
				  		<th>Score</th>
				  		<th>Time</th>
				  	</tr>
				    <%for (Score score : recentScores) 
				    { %>
				     	<tr>
				        	<td> <%=score.whenPlayed%></td>
				      	</tr>
				      	<tr>
				        	<td> <a href=#> <%=score.user.username%> </a></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.score%></td>
				      	</tr>
				      	<tr>
				        	<td> <%=score.time%></td>
				      	</tr>
				      	
				     <%} %>
				  </table>
			</div>
		</div>	
	</div>
	
	<div> <a href=#>Take Quiz</a></div>

</body>
</html>