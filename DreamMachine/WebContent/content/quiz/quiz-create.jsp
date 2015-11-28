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
  <div id="quiz-create-form-container" class="hori-center">
    <form id="quiz-create-form" class="hori-center" method="post" action="quiz-create">
      <div id="quiz-name-container">
        <div id="quiz-name" class="hori-center">
          <span id="quiz-name-header" class="quiz-header">Name your Quiz</span><br>
          <input id="quiz-name-input" type="text" name="name"><br>
        </div>
      </div>
      <div id="quiz-description" class="hori-center">
        <%
          assignment.ErrorMessages errors = (assignment.ErrorMessages) request.getSession().getAttribute("errors");
          if (errors == null) errors = new assignment.ErrorMessages();
          java.util.List<String> curErrors = errors.getErrors(assignment.Quiz.DESCRIPTION_ERROR);
		  boolean hasErrors = false;
          if (!curErrors.isEmpty()) hasErrors = true;
        %>
        <span id="quiz-description-header" class="quiz-header">Add a Description</span><br>
        <jsp:include page="/content/templates/form-textarea.jsp">
          <jsp:param name="form-id" value="quiz-create-form" />
          <jsp:param name="data-name" value="quiz-description" />
          <jsp:param name="textarea-id" value="quiz-textarea" />
          <jsp:param name="textarea-class" value="none" />
          <jsp:param name="errors" value="<%= hasErrors %>" />
        </jsp:include>
      </div>
      <div id="quiz-options" class="hori-center">
      </div>
      <div id="quiz-submit">
        <button id="quiz-submit-button" class="quiz-create-form" value="Create Quiz">Create Quiz</button>
      </div>
    </form>
  </div>
</body>
</html>