
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search-results.css">
<jsp:include page="/content/app.jsp" />
<title>results for search <%=request.getParameter("term")%></title>
</head>

<body>
	<jsp:include page="../header/top-bar.jsp" />
	<%
	java.util.ArrayList<String> results = (java.util.ArrayList<String>) request.getAttribute("searchResults");
	%>
 	<div class = "results-list">
			<%for(String user : results) {%>
				<li><button type = "button" class = "button-results">
					<a href="/DreamMachine/user/<%=user%>">
						<h1><%=user%></h1>
					</a>
				</button></li>
			<%}%>
	</div> 
</body>
</html>