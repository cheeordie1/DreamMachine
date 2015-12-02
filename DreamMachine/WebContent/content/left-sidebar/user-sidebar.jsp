
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
	

	<h1 id = "user-welcome">Buddy</h1>
	
	<div class = bottom-icons>
	
		<button type="button" class="btn btn-default btn-lg" id = "mail-button">
	    	<span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
		</button>
	    
	    <br>
	    
	    <button type="button" class="btn btn-default btn-lg" id = "history-button">
	    	<span class="glyphicon glyphicon-history" aria-hidden="true"></span>
		</button>
		
	</div>
   
	</nav>

</body>