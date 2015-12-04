<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/response-create.css">
<%@ page import="java.util.List,assignment.*" %>
<% Quiz pageQuiz = (Quiz) request.getAttribute("pageQuiz"); %>
<div id="response-create-container">
  <form method="post" action="/question-create">
    <label class="response-hdr hori-center">Create a Response Question for <%= pageQuiz.name %></label><br>
	<input type="text" class="response-question-box hori-center"></input>
	<jsp:include page="/content/templates/file-upload.jsp" />
  </form>
</div>