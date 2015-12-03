<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/question-create.css">
<link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
<jsp:include page="/content/app.jsp" />
<%@ page import="java.util.List,assignment.*" %>
<%
  Quiz pageQuiz = (Quiz) request.getAttribute("pageQuiz");
  String type = request.getAttribute("questionType").toString();
%>
<title><%= pageQuiz.name %> Question Creator</title>
</head>
<body>
  <jsp:include page="/content/header/top-bar.jsp" />
  <div id="background"></div>
  <div id="question-creator-container">
    <div id="question-type-selector">
      <%
        String responseURL = request.getRequestURL().toString() + "&questionType=" + Question.RESPONSE;
        String matchingURL = request.getRequestURL().toString() + "&questionType=" + Question.MATCHING;
        String multichoiceURL = request.getRequestURL().toString() + "&questionType=" + Question.MULTICHOICE;
      %>
      <div id="response-change" class="question-type-changer"><a class="question-type-changer-link" href=<%= responseURL %>>Response</a></div>
      <div id="matching-change" class="question-type-changer"><a class="question-type-changer-link" href=<%= matchingURL %>>Matching</a></div>
      <div id="multichoice-change" class="question-type-changer"><a class="question-type-changer-link" href=<%= multichoiceURL %>>Multiple Choice</a></div>
    </div>
    <div id="question-specific-container">
      <% if (type.equals(Question.RESPONSE)) { %>
      <jsp:include page="/content/question/response.jsp" />
      <% } else if (type.equals(Question.MATCHING)) { %>
      <jsp:include page="/content/question/matching.jsp" />
      <% } else if (type.equals(Question.MULTICHOICE)) { %>
      <jsp:include page="/content/question/multichoice.jsp" />
      <% } else { %>
      <div id="what?">What?</div>
      <% } %>
    </div>
  </div>
</body>
</html>