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
  String type = request.getAttribute("question-type").toString();
  String questionPageURL = "";
  if (type.equals(Question.RESPONSE)) {
      questionPageURL = "/content/question/response.jsp";
  } else if (type.equals(Question.MATCHING)) {
      questionPageURL = "/content/question/matching.jsp";
  } else if (type.equals(Question.MULTICHOICE)) {
      questionPageURL = "/content/question/multichoice.jsp";
  } else response.sendError(HttpServletResponse.SC_NOT_FOUND); 
%>
  
<title><%= pageQuiz.name %> Question Creator</title>
</head>
<body>
  <jsp:include page="/content/header/top-bar.jsp" />
  <div id="background"></div>
  <div id="question-creator-container" class="hori-center">
    <div id="question-type-selector">
      <%
      	String url = "/DreamMachine/question-create";
        String responseURL = url + "?question-type=" + Question.RESPONSE + "&quiz-id=" + pageQuiz.quiz_id;
        String matchingURL = url + "?question-type=" + Question.MATCHING + "&quiz-id=" + pageQuiz.quiz_id;
        String multichoiceURL = url + "?question-type=" + Question.MULTICHOICE + "&quiz-id=" + pageQuiz.quiz_id;
      %>
      <div id="response-change" class="question-type-changer"><a class="<%= type.equals(Question.RESPONSE) ? "selected" : "question-type-changer-link" %>" href=<%= responseURL %>>Response</a></div>
      <div id="matching-change" class="question-type-changer"><a class="<%= type.equals(Question.MATCHING) ? "selected" : "question-type-changer-link" %>" href=<%= matchingURL %>>Matching</a></div>
      <div id="multichoice-change" class="question-type-changer"><a class="<%= type.equals(Question.MULTICHOICE) ? "selected" : "question-type-changer-link" %>" href=<%= multichoiceURL %>>Multiple Choice</a></div>
    </div>
    <div id="question-specific-container">
      <jsp:include page="<%= questionPageURL %>" />
    </div>
  </div>
  <% if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { %>
  <jsp:include page="/content/chat/chat.jsp" />
  <% } %>
</body>
</html>