
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search-results.css">
<jsp:include page="/content/app.jsp" />
<title>results for search <%=request.getParameter("term")%></title>
</head>

<body>
	<jsp:include page="../header/top-bar.jsp" />
	<%
	java.util.ArrayList<String> userResults = (java.util.ArrayList<String>) request.getAttribute("searchUserResults");
	%>
	<div class="search-title">
		Dreamers (Users)
	</div>
 	<div class = "results-list">
			<%for(String user : userResults) {%>
				<li>
				<%if((boolean)request.getSession().getAttribute("loggedIn")) { %>
					<button type = "button" class = "button-results">
						<a href="/DreamMachine/user/<%=user%>">
							<h1><%=user%></h1>
						</a>
					</button>
				<%} else {%>
					<div class="logged-out-display"	>
						<%=user%>
					</div>	
				<%} %>
				</li>
			<%}%>
	</div> 
	
	<%
	java.util.ArrayList<assignment.Quiz> quizResults = 
		(java.util.ArrayList<assignment.Quiz>) request.getAttribute("searchQuizResults");
	%>
	<div class="search-title">
		Quizzes
	</div>
	
	<div class = "results-list">
		<%for(assignment.Quiz q : quizResults) { %>
			<li>
			<%if((boolean)request.getSession().getAttribute("loggedIn")) { %>
					<button type="button" class = "quiz-button-results">
						<a href="/DreamMachine/quiz/<%=q.quiz_id%>">				
							<h1><%=q.name%></h1>
						</a>
					</button>
				<%} else {%>
					<div class="logged-out-display"	>
						<%=q.name%>
					</div>	
				<%}%>
			<%}%>
			</li>
	</div>
</body>
</html>