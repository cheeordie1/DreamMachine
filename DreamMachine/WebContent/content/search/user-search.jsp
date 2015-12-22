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
  <jsp:include page="/content/header/top-bar.jsp" />
  <div id="search-div" class="page-div">
  <%
  %>
  <% if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { %>
  <jsp:include page="/content/chat/chat.jsp" />
  <% } %>
  </div>
</body>
</html>