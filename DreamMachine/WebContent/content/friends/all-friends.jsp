
<html>
<head>
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search-results.css">
<jsp:include page="/content/header/top-bar.jsp" />
<jsp:include page="../app.jsp"></jsp:include>
</head>

<body>
<%
	java.util.ArrayList<String> friends = new java.util.ArrayList<String>();%>
<div id="my-list" class="hori-vert-center">
<li>
	<%for(String friend : friends ) {%>
		<button type = "button" class = "button-results">
			<a href="/DreamMachine/user/<%=friend%>">
				<h1><%=friend%></h1>
			</a>
		</button>
	<%}%>	
</li>
</div>
</body>
</html>
