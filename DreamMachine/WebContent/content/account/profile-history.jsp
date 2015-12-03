<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/profile-history.css">
<%@ page import="java.util.List,assignment.*" %>
<% 
  List<User> users = User.searchByID(Integer.parseInt(request.getAttribute("pageUserID").toString()));
  User pageUser = users.get(0);
  List<Quiz> madeQuizzes = Quiz.searchByUserID(pageUser.user_id);
  List<Friend> friendships = Friend.searchByUserID(pageUser.user_id);
%>
<div id="profile-history-container" class="hori-center">
  <div id="recently-made-quizzes-container" class="history-container">
    <div id="recently-made-quizzes-title" class="profile-history-title hori-center">Recently Made</div><br>
    <% if (madeQuizzes.isEmpty()) { %>
    <div class="profile-history-empty hori-center">No Quizzes Have Been Made Yet...</div>
    <% } else { %>
    <ul class="profile-history-list">
    <%
    	   int count = 0;
           for (Quiz quiz : madeQuizzes) { 
        	   if (count++ >= 4) break;
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
	<% if(friendships.isEmpty()) { %>
    <div class="profile-history-empty hori-center"><%= pageUser.username %> Has No Friends Yet</div>
	<% 
	  } else {
	%>
	<ul class="profile-history-list">
    <%
    	   int count = 0;
           for (Friend friendship : friendships) {
        	   if (count++ >= 4) break;
        	   List<User> friendUsers;
        	   if (friendship.friend_a_user_id == pageUser.user_id)
        		   friendUsers = User.searchByID(friendship.friend_b_user_id);
        	   else
        		   friendUsers = User.searchByID(friendship.friend_a_user_id);
        	   User friendUser = friendUsers.get(0);
        	   String url = "/DreamMachine/user/" + friendUser.user_id;
    %>
    <li><span class="profile-history-text hori-center"><a href=<%= url %> class="profile-history-link"><%= friendUser.username %></a> friended on <%= friendship.created_at.toString() %></span></li>
    <%	   } 
       String friendsURL = "/DreamMachine/friends?user_id=" + pageUser.user_id;
    %>
    </ul>
	<span class="profile-history-link2"><a href=<%= friendsURL %>>See All Friends...</a></span>
	<% } %>
  </div>
</div>