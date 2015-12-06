<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/response-create.css">
<script src="/DreamMachine/content/response-adder.js"></script>
<%@ page import="java.util.List,assignment.*" %>
<% Quiz pageQuiz = (Quiz) request.getAttribute("pageQuiz"); %>
<div id="response-create-container">
  <%
    ErrorMessages errors = (ErrorMessages) request.getSession().getAttribute("errors");
    if (errors == null) errors = new ErrorMessages(); 
    List<String> messages = errors.getErrors(Question.QUESTION_ERROR);
  %>
  <form id="response-question-form" method="post" action="question-create" enctype="multipart/form-data">
    <input type="hidden" name="question-type" value=<%= Question.RESPONSE %>>
    <input type="hidden" name="quiz-id" value=<%= pageQuiz.quiz_id %>>
    <label class="response-hdr hori-center">Create a Response Question for <%= pageQuiz.name %></label><br>
	<input type="text" name="question" class="<%= "response-question-box hori-center" + (messages.isEmpty() ? "" : " error-box") %>">
    <% for (String message : messages) { %>
    <div class="error-msg hori-center"><%= message %></div>
    <% } %>
	<div id="response-photo-container">
	  <jsp:include page="/content/templates/file-upload.jsp" />
	</div>
	<% messages = errors.getErrors(Response.ANSWER_ERROR); %>
	<div id="response-answer-container" class="<%= "hori-center" + (messages.isEmpty() ? "" : " error-box")  %>"></div>
	<% for (String message : messages) { %>
    <div class="error-msg hori-center"><%= message %></div>
    <% } %>
    <button id="response-add-answer" class="hori-center" type="button">Add an Answer</button><br>
	<input type="hidden" name="num-answers" id="response-num-answers" value=0>
	<script>new responseAdder("response-answer-container", "response-num-answers", "response-add-answer")</script>
	<div id="response-options-container" class="hori-center">
	  <div class="response-options-sub-container">
	    <label class="response-sub-hdr">Choose whether answers must be in order.</label>
	    <input id="response-order-option" type="checkbox" name="order"><br>
	  </div>
	  <% messages = errors.getErrors(Response.SUBSET_ERROR); %>
	  <div class="response-options-sub-container">
	    <label class="response-sub-hdr">How many of the options must be correct?</label>
	    <input id="response-subset-option" class="<%= (messages.isEmpty() ? "none" : "error-box") %>" type="text" name="subset"><br>
	  </div>
	  <%    for (String message : messages) { %>
      <div class="error-msg hori-center"><%= message %></div>
      <% } %>  
	</div><br>
	<button for="response-question-form" id="response-submit-button" class="question-submit hori-center">Add Question</button>
  </form>
</div>