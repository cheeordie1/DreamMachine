<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search.css">
<jsp:include page="/content/app.jsp" />
<title>Search For Quizzes</title>
</head>
<body>
  <%@ page import="java.util.List,assignment.*" %>
  <jsp:include page="/content/header/top-bar.jsp" />
  <jsp:include page="/content/layout/nature-background.jsp" />
  <div id="search-div" class="page-div">
  <% 
    List<User> results = (List<User>) request.getAttribute("searchResults");
    if (results.isEmpty()) {
  %>
    
  <% } else { %>
    	
  <% } %>
  <% if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { %>
  <jsp:include page="/content/chat/chat.jsp" />
  <% } %>
  </div>
</body>
</html>