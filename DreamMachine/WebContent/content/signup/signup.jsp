<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="assets/stylesheets/signup.css">
<jsp:include page="../app.jsp" />
<title>Enter Account Info</title>
</head>
<body>
  <jsp:include page="../header/top-bar.jsp" />
  <jsp:include page="../layout/galaxy-background.jsp" />
  <div id="signup-form-container">
    <form id="signup-form" method="post" action="signup">
      <label>Enter a Username</label><br>
      <input type="text" name="username"><br>
      <label>Enter a Password</label><br>
      <input type="password" name="password"><br>
      <label>Retype Password</label><br>
      <input type="password" name="re-password"><br>
      <div id="file-selector">
        <input id="fake-file" type="button" value="Upload Photo">
        <input id="real-file" type="file" name="photo">
      </div>
    </form>
    <script src="assets/javascripts/signup.js"></script>
  </div>
</body>
</html>