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
<%
  if (request.getSession().getAttribute("loggedIn").toString().equals("true")) { 
	int loggedInUserID = (int) request.getSession().getAttribute("uid");
    if (loggedInUserID != pageUser.user_id) {
%>
  <div class="friend-request-container">
    <%
      List<Friend> friendshipList = Friend.searchByUserIDUserID(loggedInUserID, pageUser.user_id);
      if (friendshipList.isEmpty()) {
    %>
      <div id="add-friend-container" class="friend-request-div hori-center">
        <form id="add-friend-form" method="post" action="/DreamMachine/add-friend">
          <input type="hidden" name="sender" value="<%= loggedInUserID %>">
          <input type="hidden" name="receiver" value="<%= pageUser.user_id %>">
          <input type="hidden" name="pageURL" value="<%= "/DreamMachine/user/" + pageUser.username %>">
 		  <input id="send-friend-request" class="friend-button" type="submit" value="Add Friend">
        </form>
      </div>
    <%
      } else {
    	Friend friendship = friendshipList.get(0);
    	if (loggedInUserID == friendship.sender) {
    %>
      <div class="pending-request-div">
        <div class="pending-request hori-center">
          <span class="pending-request-span">Friend Request Sent...</span>
        </div>
      </div>    
    <% } else { %>
 
    <% }} %>
  </div>
<% 
    }
  } 
%>