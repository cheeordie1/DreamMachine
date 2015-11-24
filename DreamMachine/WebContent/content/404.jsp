<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/404.css">
<jsp:include page="/content/app.jsp" />
<title>Error 404</title>
</head>
<body>
  <div id="error-background">
    <img id="error-img" src="/DreamMachine/assets/images/error.png">
    <div id="error-text-container" class="hori-center">
      <div id="error-text" class="hori-center">That page doesn't exist, and now you've found our source code!!</div>
      <div id="error-link" class="hori-center"><a id="error-link-text" href="/DreamMachine/home">Please, Just Go Back Home.</a></div>
    </div>
  </div>
</body>
</html>