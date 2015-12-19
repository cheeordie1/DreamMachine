<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/home.css">
<jsp:include page="/content/app.jsp" />
<title>Welcome to Dream Machine</title>
</head>
<body>
  <jsp:include page="../header/top-bar.jsp" />
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
</body>
<% if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { %>
<jsp:include page="/content/chat/chat.jsp" />
<% } %>
</html>