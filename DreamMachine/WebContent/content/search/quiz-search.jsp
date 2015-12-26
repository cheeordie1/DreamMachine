<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search.css">
<jsp:include page="/content/app.jsp" />
<link href='https://fonts.googleapis.com/css?family=Lora|Ubuntu' rel='stylesheet' type='text/css'>
<script src="/DreamMachine/assets/javascripts/tag.js"></script>
<title>Search For Quizzes</title>
</head>
<body>
  <%@ page import="java.util.List,assignment.*,java.text.*" %>
  <jsp:include page="/content/layout/nature-background.jsp" />
  <jsp:include page="/content/header/top-bar.jsp" />
  <% 
    List<Quiz> results = (List<Quiz>) request.getAttribute("searchResults");
    if (results.isEmpty()) {
  %>
    <div class="no-results-container hori-center">
      <span class="no-results-span hori-center">No Quizzes matched the search "<%= request.getAttribute("term").toString() %>"</span>
    </div>    
  <% } else { %>
   <div class="results-container hori-center">
    <span class="results-title-span hori-center">
      <% String quizzesString = results.size() + (results.size() == 1 ? " Quiz" : " Quizzes"); %>
      <%= quizzesString %> found matching the search "<%= request.getAttribute("term").toString() %>"
    </span>
    <hr class="results-divider hori-center">
    <% 
      DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
      for (Quiz quiz : results) {
    	List<User> users = User.searchByID(quiz.user_id);
    	User creator = users.get(0);
    	String quizTagContainerID = quiz.quiz_id + "-tag-container";
    	List<Tag> tags = Tag.searchByQuizID(quiz.quiz_id);
    %>
      <div class="results-entry-div">
        <div class="quiz-info-container">
          <a class="name-link" href="<%= "/DreamMachine/quiz/" + quiz.quiz_id %>"><%= quiz.name %></a><br>
          <span class="info-text">Created By: <a class="quiz-data-link" href="<%= "/DreamMachine/user/" + creator.username %>"><%= creator.username %></a></span><br>
          <span class="info-text">Date: <span class="quiz-data"><%= sdf.format(quiz.created_at).toString() %></span></span>
        </div>
        <div class="quiz-description-container">
          <div class="quiz-description-div">
            <span class="quiz-description-hdr">Description: </span>
            <span class="quiz-description"><%= quiz.description %></span>
          </div>
          <div id="<%= quizTagContainerID %>" class="quiz-tag-container"><span class="quiz-tag-span">Tags: </span></div>
        </div>
        <script>
          var tagger = new TagBox ("<%= quizTagContainerID %>", null);
          tagger.nullify ();
        </script>
        <% for (Tag tag : tags) { %>
          <script>tagger.manualAddTag("<%= tag.tag %>");</script>
        <% } %>
      </div>
      <hr class="results-divider hori-center">
    <% } %>
  </div> 	
  <% } %>
  <% if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { %>
  <jsp:include page="/content/chat/chat.jsp" />
  <% } %>
</body>
</html>