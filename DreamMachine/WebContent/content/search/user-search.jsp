<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search.css">
<jsp:include page="/content/app.jsp" />
<title>Search for Users</title>
</head>
<body>
  <%@ page import="java.util.List,assignment.*" %>
  <jsp:include page="/content/layout/nature-background.jsp" />
  <jsp:include page="/content/header/top-bar.jsp" />
  <%
    List<User> results = (List<User>) request.getAttribute("searchResults"); 
    if (results.isEmpty()) {
  %>
  <div class="no-results-container hori-center">
    <span class="no-results-span hori-center">No Users matched the search "<%= request.getAttribute("term").toString() %>"</span>
  </div>
  <% } else { %>
  <div class="results-container hori-center">
    <span>
      <% 
        String quizzesString = results.size() + (results.size() == 1 ? " User" : " Users");
      %>
      <%= quizzesString %> found matching the search "<%= request.getAttribute("term").toString() %>"
    </span>
  </div>
  <% } %>
  <% if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { %>
  <jsp:include page="/content/chat/chat.jsp" />
  <% } %>
</body>
</html>