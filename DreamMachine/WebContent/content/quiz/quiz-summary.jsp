
<%@page import = "assignment.DBConnection"%>
<%@page import = "assignment.Quiz"%>
<%@page import = "assignment.User"%>
<%@page import = "assignment.QuizStats"%>
<%@page import = "assignment.Score"%>
<%@page import = "java.util.ArrayList"%>
<%@page import = "java.util.List"%>
<%@page import = "java.text.NumberFormat" %>
<%@page import = "java.sql.ResultSet" %>
<%@page import = "java.sql.SQLException" %>


<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	int quiz_id = 1; //(Integer) request.getAttribute("quiz_id"); 
	int user_id = 1; //(Integer) request.getAttribute("user_id");
	Quiz quiz = Quiz.searchByID(quiz_id).get(0);
	User creator = User.searchByID(user_id).get(0);
	List<Score> allScores  = Score.searchByQuizID(quiz_id);
	
	ArrayList<Score> userScores = QuizStats.pastPerformances(user_id, quiz_id);
	ArrayList <Score> bestScores = QuizStats.highestPerformers(allScores);
	ArrayList <Score> topDailyScores = QuizStats.highestPerformersPastDay(allScores);
	ArrayList <Score> recentScores = QuizStats.recentPerformances(allScores);
	
%>

<jsp:include page="/content/app.jsp" />
<title>What Quizzes Do You Conjure</title>
</head>

<body>
	<!-- Link to user page  -->
	<div id="creator-page-link">
		<h2>Created by <a href=#><%=creator.username%></a></h2>
	</div>
	
	<div id="description"> 
		<h3>Description:</h3>
		<p><%=quiz.description%></p>
	</div>
	
	<div id="stats"> 
			<div id="pop-quizzes-cont" class="well">
				<h3>Quiz Statistics:</h3>
				<table class="table table-condensed">
					<thead>
					  	<tr>
					  		<th>Date Created</th> 
					  		<th>Times Played</th>
					  		<th>Average Score</th>
					  		<th>High Score</th>
					  		<th>Low Score</th>
					  	</tr>
				  	</thead>
				  		<tbody>
					     	<tr>
					        	<td> <%=quiz.created_at%></td>
					        	<td> <%=QuizStats.timesPlayed(allScores)%> </td>
					        	<td> <%=QuizStats.averageScore(allScores)%></td>
					        	<td> <%=QuizStats.highScore(allScores)%></td>
					        	<td> <%=QuizStats.lowScore(allScores)%></td>
					      	</tr>
				      	</tbody>
				</table>
			</div>
		</div>
		
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
				        	<td> <%=score.finishTime%></td>
				        	<td> <a href=/DreamMachine/user?<%=score.user_id%>> <%=score.user.username%></a></td>
				        	<td> <%=score.score%></td>
				        	<td> <%=score.getDuration()%></td>
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
				        	<td> <%=score.finishTime%></td>
				        	<td> <a href=/DreamMachine/user?<%=score.user_id%>> <%=score.user.username%></a></td>
				        	<td> <%=score.score%></td>
				        	<td> <%=score.getDuration()%></td>
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
				        	<td> <%=score.finishTime%></td>
				        	<td> <a href=/DreamMachine/user?<%=score.user_id%>> <%=score.user.username%></a></td>
				        	<td> <%=score.score%></td>
				        	<td> <%=score.getDuration()%></td>
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
				        	<td> <%=score.finishTime%></td>
				        	<td> <a href=/DreamMachine/user?<%=score.user_id%>> <%=score.user.username%></a></td>
				        	<td> <%=score.score%></td>
				        	<td> <%=score.getDuration()%></td>
				      	</tr>
				      	
				     <%}%>
				  </table>
			</div>
		</div>	
	</div>
	<br>
	<div> <a href=#>Take Quiz</a></div>

</body>
</html>