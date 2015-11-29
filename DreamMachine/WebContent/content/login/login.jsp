<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/login.css">
<jsp:include page="../app.jsp" />
<title>Enter Login Info</title>
</head>
<body>
  <jsp:include page="/content/header/top-bar.jsp" />
  <jsp:include page="/content/layout/splatter-background.jsp" />
  <div id="login-form-container" class="hori-center">
    <%
      String title;
      if (request.getParameter("notify") != null)
        title = request.getParameter("notify").toString();
      else
    	title = "Welcome Back, Dreamer!";
    %>
    <div id="login-title" class="hori-center"><%= title %></div><br>
    <form id="login-form" class="hori-center" method="post" action="login">
      <label class="login-subtitles">Username</label><br>
      <input type="text" class="login-input" name="username"><br><br>
      <label class="login-subtitles">Password</label><br>
      <input type="password" class="login-input" name="password"><br><br>
      <%
        assignment.ErrorMessages errors = (assignment.ErrorMessages) request.getSession().getAttribute("errors");
        if (errors == null) errors = new assignment.ErrorMessages(); 
        java.util.List<String> messages = errors.getErrors(assignment.User.LOGIN_ERROR);
        for (String message : messages) { 
      %>
        <div class="error-msg"><%= message %></div>
      <% } %>
      <input id="login-submit" class="hori-center" type="submit" value="Enter Dream">
    </form>
  </div>
</body>
</html>