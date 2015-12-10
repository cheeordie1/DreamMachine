<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search.css">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>results for search <%=request.getParameter("term")%></title>
</head>
<body>
	<%
	java.util.ArrayList<String> results = (java.util.ArrayList<String>) request.getAttribute("searchResults");
	%>
	<div>
		<%for(String user : results) {%>
			<a href="/DreamMachine/user/<%=user%>">
				<span><%=user%></span>
			</a>
		<%}%>
	</div>
</body>
</html>