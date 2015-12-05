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
  <jsp:include page="../header/top-bar.jsp" />

  <div id="home-div">
    <jsp:include page="words.jsp" />
    <div id="announcement-container">
		<h3>Announcements</h3>
    	<div id="announcement-sect" class="well">
 			<table class="table table-condensed">
   		    	<tbody>
     			   <tr>
     		 		 <td>New Quiz Created!! take it now!</td>
     		   	   </tr>
			    </tbody>
		    </table>
        </div>
    </div>
    
    <div id="announcement-container">
		<h3>Popular Quizzes</h3>
    	<div id="announcement-sect" class="well">
 			<table class="table table-condensed">
   		    	<tbody>
     			   <tr>
     		 		 <td>Play bob on the drums</td>
     		   	   </tr>
			    </tbody>
		    </table>
        </div>
    </div>
    
        <div id="announcement-container">
		<h3>Friend Activity</h3>
    	<div id="announcement-sect" class="well">
 			<table class="table table-condensed">
   		    	<tbody>
     			   <tr>
     		 		 <td>Sage played a quiz</td>
     		   	   </tr>
			    </tbody>
		    </table>
        </div>
    </div>
  </div>
  
     <div id="announcement-container">
		<h3>Recent Activity</h3>
    	<div id="announcement-sect" class="well">
 			<table class="table table-condensed">
   		    	<tbody>
     			   <tr>
     		 		 <td>You created a quiz</td>
     		   	   </tr>
			    </tbody>
		    </table>
        </div>
    </div>
  
  
  
  
  <% if ((boolean)request.getSession().getAttribute("loggedIn")) { %>
  <jsp:include page="/content/chat/client-chat.jsp" />
  <% } %>
</body>
</html>