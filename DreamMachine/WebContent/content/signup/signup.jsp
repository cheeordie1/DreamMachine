<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/signup.css">
<jsp:include page="/content/app.jsp" />
<title>Enter Account Info</title>
</head>
<body>
  <jsp:include page="/content/header/top-bar.jsp" />
  <jsp:include page="/content/layout/galaxy-background.jsp" />
  <div id="signup-form-container" class="hori-center">
    <%
      String title = "Create New Account to Enter the Dream";
      if (request.getParameter("notify") != null) 
        title = request.getParameter("notify").toString();
    %>
    <div id="signup-title" class="hori-center"><%= title %></div><br>
    <%
      assignment.ErrorMessages errors = (assignment.ErrorMessages) request.getSession().getAttribute("errors");
      if (errors == null) errors = new assignment.ErrorMessages(); 
      java.util.List<String> messages = errors.getErrors(assignment.User.EMPTY_ERROR);
      for (String message : messages) { 
    %>
      <div class="error-msg"><%= message %></div>
    <% } %>
    <form id="signup-form" class="hori-center" method="post" action="signup" enctype="multipart/form-data">
      <label class="signup-subtitles">Enter a Username</label><br>
      <% messages = errors.getErrors(assignment.User.USERNAME_ERROR); %>
        <input type="text" class=<%= !messages.isEmpty() ? "'signup-input error-box'" : "signup-input" %> name="username">
        <br><% for (String message : messages) { %>
        <div class="error-msg"><%= message %></div>
        <% } %><br>
        <label class="signup-subtitles">Enter a Password</label><br>
      <% messages = errors.getErrors(assignment.User.PASSWORD_ERROR); %>
        <input type="password" class=<%= !messages.isEmpty() ? "'signup-input error-box'" : "signup-input" %> name="password">
        <br><% for (String message : messages) { %>
        <div class="error-msg"><%= message %></div>
        <% } %><br>
      <label class="signup-subtitles">Retype Password</label><br>
      <% messages = errors.getErrors(assignment.User.PASSWORD_DUP_ERROR); %>
        <input type="password" class=<%= !messages.isEmpty() ? "'signup-input error-box'" : "signup-input" %> name="re-password">
        <br><% for (String message : messages) { %>
        <div class="error-msg"><%= message %></div>
        <% } %>
        <br>
      <jsp:include page="/content/templates/file-upload.jsp" />
      <% messages = errors.getErrors(assignment.User.PHOTO_ERROR);
         for (String message : messages) { 
      %>
        <div class="error-msg"><%= message %></div>
      <% } %>
      <input id="signup-submit" class="hori-center" type="submit" value="Enter Dream">
    </form>
  </div>
</body>
</html>