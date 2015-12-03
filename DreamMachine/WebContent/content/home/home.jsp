
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
  
  
</head>
<body>

	<div class = "col-md-12" id = "title-bar">
		<h1>Dream Machine</h1>
		<jsp:include page="../left-sidebar/user-sidebar.jsp" />
	</div>
		
	
</body>

</html>
