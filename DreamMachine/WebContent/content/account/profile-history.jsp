<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/profile-history.css">
<%@ page import="java.util.List,assignment.*" %>
<% 
  List<User> users = User.searchByID(Integer.parseInt(request.getAttribute("pageUserID").toString()));
  User pageUser = users.get(0);
  List<Quiz> madeQuizzes = Quiz.searchByUserID(pageUser.user_id);
%>
<div id="profile-history-container" class="hori-center">
  <div id="recently-made-quizzes-container" class="history-container">
    <div id="recently-made-quizzes-title" class="profile-history-title hori-center">Recently Made</div><br>
    <% if (madeQuizzes.isEmpty()) { %>
    <div class="profile-history-empty hori-center">No Quizzes Have Been Made Yet...</div>
    <% } else { %>
    <ul class="profile-history-list">
    <%
           for (Quiz quiz : madeQuizzes) { 
        	   String url = "/DreamMachine/quiz/" + quiz.quiz_id;
    %>
    <li><span class="profile-history-text hori-center"><a href=<%= url %> class="profile-history-link"><%= quiz.name %></a> created on <%= quiz.created_at.toString() %></span></li>
    <%	   } 
       String quizzesURL = "/DreamMachine/quizzes?user_id=" + pageUser.user_id;
    %>
    </ul>
    <span class="profile-history-link2"><a href=<%= quizzesURL %>>See more quizzes...</a></span>
    <% } %>
  </div>
  <div id="recently-taken-quizzes-container" class="history-container">
    <div id="recently-taken-quizzes-container" class="profile-history-title hori-center">Recently Taken</div><br>

  </div>
  <div id="recently-friend-container" class="history-container">
    <div id="recently-friended-title" class="profile-history-title hori-center">Recently Friends</div><br>

  </div>
</div>