<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/friend-response.css">
<jsp:include page="/content/app.jsp" />
<title>Friend Requests</title>
</head>
<body>

	<jsp:include page="../header/top-bar.jsp" />
	
	<div class = "friend-response-form">
		<%String sender = (String) request.getAttribute("sender"); %>
		<h2><label><%=sender%></label><br></h2>
		<form action="/DreamMachine/response" method="post">
		
			accept <input type="radio" name="name" value="accept"><br>
			reject <input type="radio" name="name" value="reject"><br>
			block <input type="radio" name="name" value="block"><br>
			
			
			<input type="submit" value="submit" id = "friend-request-submit">
			
			
			<input type="hidden" name="sender" value="<%=sender%>">
		</form>
	</div>


</body>