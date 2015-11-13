<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="assets/stylesheets/home.css">
<jsp:include page="../app.jsp">
  <jsp:param value="" name=""/>
</jsp:include>
<title>Welcome to Dream Machine</title>
</head>
<body>
  <jsp:include page="../header/top-bar.jsp">
    <jsp:param value="" name=""/>
  </jsp:include>
  <div id="home-div">
    <jsp:include page="words.jsp">
      <jsp:param value="" name=""/>
    </jsp:include>
    <div id="leaderboards-div">
      <div id="top-played-container">
        <jsp:include page="top-played.jsp">
	      <jsp:param value="" name=""/>
        </jsp:include>
      </div>
      <div id="most-quizzes-container">
        <jsp:include page="most-quizzes.jsp">
	      <jsp:param value="" name=""/>
        </jsp:include>
      </div>
      <div id="recently-created-container">
        <jsp:include page="recently-created.jsp">
	      <jsp:param value="" name=""/>
        </jsp:include>
      </div>
    </div>
  </div>
</body>
</html>