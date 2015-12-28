<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/account.css">
<jsp:include page="/content/app.jsp" />
<title><%= ((assignment.User) request.getAttribute("pageUser")).username %>'s Page</title>
</head>
<body>
  <jsp:include page="/content/header/top-bar.jsp" />
  <jsp:include page="/content/account/profile-box.jsp" />
  <jsp:include page="/content/account/profile-history.jsp" />
  <% if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { %>
  <jsp:include page="/content/chat/chat.jsp" />
  <% } %>
</body>
</html>