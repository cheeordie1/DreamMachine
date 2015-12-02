<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search-results.css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>results for search <%=request.getParameter("term")%></title>
</head>
<body>
	<%
	java.util.ArrayList<assignment.User> results =
		(java.util.ArrayList<assignment.User>) request.getAttribute("searchResults");
	java.util.ArrayList<assignment.User> blockedCache =
		(java.util.ArrayList<assignment.User>) request.getSession().getAttribute("blockedFriends");
	/* determine the links to show (not your own, not blocked) */
	java.util.ArrayList<String> displayResults = new java.util.ArrayList<String>();
	for(assignment.User u : results) {
		if(blockedCache != null && !blockedCache.contains(u.username))
			displayResults.add(u.username);
	}
	%>
	<div>
		<%for(String user : displayResults) {%>
			<a href="/DreamMachine/user/<%=user%>">
				<span><%=user%></span>
			</a>
		<%}%>
	</div>
</body>
</html>