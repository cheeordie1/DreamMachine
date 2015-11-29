<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/quiz-create.css">
<link href='https://fonts.googleapis.com/css?family=Lato' rel='stylesheet' type='text/css'>
<jsp:include page="/content/app.jsp" />
<title>What Quizzes Do You Conjure</title>
</head>
<body>
  <jsp:include page="/content/header/top-bar.jsp" />
  <jsp:include page="/content/layout/blurry-city-background.jsp" />
  <div id="quiz-title" class="hori-center">Make a Quiz</div>
  <div id="quiz-create-form-container" class="hori-center">
    <form id="quiz-create-form" class="hori-center" method="post" action="quiz-create">
      <%
        assignment.ErrorMessages errors = (assignment.ErrorMessages) request.getSession().getAttribute("errors");
        if (errors == null) errors = new assignment.ErrorMessages();
        java.util.List<String> messages = errors.getErrors(assignment.Quiz.NAME_ERROR);
        String nameClass = !messages.isEmpty() ? "'error-box'"  : "'none'";
      %>
      <div id="quiz-name-container">
        <div id="quiz-name" class="hori-center">
          <span id="quiz-name-header" class="quiz-header">Name your Quiz</span><br>
          <input id="quiz-name-input" class=<%= nameClass %> type="text" name="quiz-name"><br>
          <% for (String message : messages) { %>
            <div class="error-msg"><%= message %></div>
          <% } %>
        </div>
      </div>
      <div id="quiz-description" class="hori-center">
        <%
          messages = errors.getErrors(assignment.Quiz.DESCRIPTION_ERROR);
		  boolean hasErrors = false;
          if (!messages.isEmpty()) hasErrors = true;
        %>
        <span id="quiz-description-header" class="quiz-header">Add a Description</span><br>
        <jsp:include page="/content/templates/form-textarea.jsp">
          <jsp:param name="form-id" value="quiz-create-form" />
          <jsp:param name="data-name" value="quiz-description" />
          <jsp:param name="textarea-id" value="quiz-textarea" />
          <jsp:param name="textarea-class" value="none" />
          <jsp:param name="errors" value="<%= hasErrors %>" />
        </jsp:include>
        <% for (String message : messages) { %>
          <div class="error-msg"><%= message %></div>
        <% } %>
      </div>
      <div id="quiz-options" class="hori-center">
        <label id="single-page-header" class="quiz-options-header" for="single-page">Select whether the quiz is on one or multiple pages</label><br>
        <div id="single-page-container" class="quiz-radio-container">
          <input id="single-page" type="radio" name="single-page" value="single" checked><label id="single-page-label" for="single-page">Single Page</label>
          <input id="multiple-pages" type="radio" name="single-page" value="multiple"><label id="multiple-page-label" for="multiple-pages">Multiple Pages</label>
        </div>
        <label id="random-questions-header" class="quiz-options-header" for="random-questions">Select whether the quiz's questions are asked in order</label><br>
        <div id="random-questions-container" class="quiz-radio-container">
          <input id="in-order" type="radio" name="random-questions" value="in-order" checked><label id="in-order-label" for="in-order">In Order</label>
          <input id="random" type="radio" name="random-questions" value="random"><label id="random-label" for="random">Random</label>
        </div>
        <label id="practice-mode-header" class="quiz-options-header" for="practice-mode">Select whether the quiz may be taken in <span id="practice-mode">Practice Mode</span></label>
		<input id="practice-mode-box" type="checkbox" name="practice-mode">
      </div>
      <div id="quiz-submit">
        <button id="quiz-submit-button" class="quiz-create-form" value="Create Quiz">Create Quiz</button>
      </div>
    </form>
  </div>
</body>
</html>