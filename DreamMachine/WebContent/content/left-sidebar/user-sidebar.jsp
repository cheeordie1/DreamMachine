
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/user-sidebar.css">	
</head>
<head>
<body>


	<nav id="left" class="column">
    <%@ page import="assignment.User, java.util.List" %>
    
    
	<div class = "profile-picture">
		<img id="user-img" src="/DreamMachine/assets/images/mr-bean.jpeg" alt="user pic" class="img-circle">
	</div>
	
	<%
        HttpSession sess = request.getSession();
        if (sess.getAttribute("loggedIn").toString().equals("true")) {
      %>
      
	<h1 id = "user-welcome"><%=sess.getAttribute("username") %></h1>
	
	<div id="login-bar-container">
      <div id="logged-in-text">
        <div id="logout-options">
        <center>
	      <br>
	      <br>
          <a class = "btn btn-default" role = "button" id="account-link" class="login-link" href=<%= "/DreamMachine/user/" + sess.getAttribute("username") %> >Account</a>
		  <br>
		  <br>
          <a class = "btn btn-default" role = "button" id="logout-link" class="logout-link" href="/DreamMachine/logout">Logout</a>
          <br>
          <br>
          <a class = "btn btn-default" role = "button" id="messages-link" class="" href="">Messages</a>
		  <br>
		  <br>
		  <a class = "btn btn-default" role = "button" id="history-link" class="" href="">History</a>
          </center>
        </div>
      </div>
      <% } else { %>
      <div id="not-logged-in-text">
        <a id="login-link" class="login-link" href="/DreamMachine/login">Login</a>
        <a id="signup-link" class="login-link" href="/DreamMachine/signup">Sign Up</a>
      </div>
      <% } %>
	</div>
   
	</nav>

</body>