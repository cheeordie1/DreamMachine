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
  <div id="signup-form-container" class="hori-center">
    <div id="signup-title" class="hori-center">Create New Account to Enter the Dream</div><br>
    <form id="signup-form" class="hori-center" method="post" action="signup">
      <label class="signup-subtitles">Enter a Username</label><br>
      <input type="text" class="signup-input" name="username"><br><br>
      <label class="signup-subtitles">Enter a Password</label><br>
      <input type="password" class="signup-input" name="password"><br><br>
      <label class="signup-subtitles">Retype Password</label><br>
      <input type="password" class="signup-input" name="re-password"><br><br>
      <div id="file-selector">
        <input id="fake-file" type="button" value="Upload Photo">
        <input id="real-file" type="file" name="photo">
      </div>
      <input id="signup-submit" class="hori-center" type="submit" value="Enter Dream">
    </form>
    <script src="assets/javascripts/signup.js"></script>
  </div>
</body>
</html>