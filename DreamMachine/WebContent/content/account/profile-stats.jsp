<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/profile-stats.css">
<%@ page import="java.util.List,assignment.*, java.text.*" %>
<%
  User pageUser = (User) request.getAttribute("pageUser");
  List<Friend> friends = Friend.searchByUserID(pageUser.user_id);
  List<Quiz> quizzesMade = Quiz.searchByUserID(pageUser.user_id);
  DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
%>
<div class="profile-stats-container">
  <div id="profile-stats-title" class="profile-stats-title hori-center">Statistics</div>
  <div class="profile-stats-div">
    <div class="profile-statistic"><span class="profile-statistic-hdr">Friends: <span class="profile-data"><%= friends.size() %></span></span></div>
    <div class="profile-statistic"><span class="profile-statistic-hdr">Joined: <span class="profile-data"><%= sdf.format(pageUser.created_at).toString() %></span></span></div>
    <div class="profile-statistic"><span class="profile-statistic-hdr">Quizzes Taken: <span class="profile-data"><%= "implement" %></span></span></div>
    <div class="profile-statistic"><span class="profile-statistic-hdr">Quizzes Made: <span class="profile-data"><%= quizzesMade.size() %></span></span></div>
  </div>
</div>