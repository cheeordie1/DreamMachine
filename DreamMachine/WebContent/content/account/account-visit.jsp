<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/top-bar.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<jsp:include page="/content/app.jsp" />
<title><%= request.getAttribute ("pageUser").toString () %>'s Page</title>
</head>
<body>
<div id="top-bar-container">
  <div id="logo">
    <a id="logo-link" href="/DreamMachine/home">
      <img id="logo-img" src="/DreamMachine/assets/images/logo.png">
    </a>
  </div>
  <jsp:include page="/content/header/search-bar.jsp" />
  
</div>
</body>
</html>