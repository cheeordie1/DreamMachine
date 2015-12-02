
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/home.css">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <jsp:include page="/content/app.jsp" />
  <title>Welcome to Dream Machine</title>
  <%@ page import="assignment.User, java.util.List" %>
  
  <%
     boolean loggedIn = (boolean) request.getSession().getAttribute("loggedIn");
  %>
</head>
<head>
<body>
	<jsp:include page="../header/top-bar.jsp" />

    
	<div id="container">
	    <jsp:include page="../content/main-content.jsp" />

		<jsp:include page="../left-sidebar/user-sidebar.jsp" />
		
		<jsp:include page="../header/feed-sidebar.jsp" />
		

	</div>

</body>

</html>


<!-- %@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/home.css">
<jsp:include page="/content/app.jsp" />
<script src="jquery.js"></script> 
<title>Welcome to Dream Machine</title>
</head>
<body>
  <jsp:include page="../header/top-bar.jsp" />
  <div id="includedContent"></div>
  <div id="home-div">
    <jsp:include page="words.jsp" />
    <div id="leaderboards-div">
      <div id="top-played-container">
        <jsp:include page="top-played.jsp" />
      </div>
      <div id="most-quizzes-container">
        <jsp:include page="most-quizzes.jsp" />
      </div>
      <div id="recently-created-container">
        <jsp:include page="recently-created.jsp" />
      </div>
    </div>
  </div>
  <jsp:include page="/content/client-chat.jsp" />
</body>
</html-->