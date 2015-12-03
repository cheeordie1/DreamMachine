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


	<div class = "col-md-12 no-padding" id = "title-bar">
		<h1><center>Dream Machine</center></h1>
	</div>
	
	<div class = "login-background">
	
		<div id="login-form-container" class="hori-center">
		    <form id="login-form" class="hori-center" method="post" action="login">
		      <center>
		      	  <br>
		      	  <br>
			      <input type="text" class="form-control input-lg" id = "username-content" placeholder= "username"><br><br>
			      <input type="password" class="form-control input-lg" id = "password-content" placeholder = "password"><br><br>
			      <%
			        assignment.ErrorMessages errors = (assignment.ErrorMessages) request.getSession().getAttribute("errors");
			        if (errors == null) errors = new assignment.ErrorMessages(); 
			        java.util.List<String> messages = errors.getErrors(assignment.User.LOGIN_ERROR);
			        for (String message : messages) { 
			      %>
			        <div class="error-msg"><%= message %></div>
			      <% } %>
			      <input id="login-submit" class="hori-center" type="submit" value="Enter Dream">
			      <br>
			      <br>
			      
			    </center>
		    </form>
		  </div>
	
	</div>

  
</body>
</html>