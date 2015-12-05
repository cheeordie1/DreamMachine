<%@page import="assignment.User, assignment.Score, assignment.Quiz, assignment.Friend"%>
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/profile-box.css">
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<div id="profile-container" class="hori-center">
  <div id="profile-photo-container">
    <%
      int uid = (Integer) request.getAttribute("pageUserID"); 
      java.util.List<assignment.Photo> photos = assignment.Photo.searchById((int)request.getAttribute("profilePic"));
      assignment.Photo photo = photos.get(0);
      String imgStyle = photo.height > photo.width ? "height:300px" : "width:300px";
    %>
    <img id="profile-photo" class="hori-vert-center" style=<%= imgStyle %> src=<%= "/DreamMachine/image/" + request.getAttribute("profilePic").toString() %>>
  </div>
  <% 
    String name = request.getAttribute("pageUser").toString(); 
    int fontSize = name.length() > 22 ? (name.length() * 30 / 22) : 30;
  %>
  <div id="profile-name" style=<%= "font-size:" + fontSize  + "px" %>><%= request.getAttribute("pageUser").toString() %></div>
  <div id="profile-info-container">
    <div id="profile-info-title" class="profile-info-title hori-center">Statistics</div>
    <div id="profile-info-recently-taken-container">
      <ul id="profile-info-recently-taken-list">
        
        <li>Quizzes Taken: <%=Score.searchByUserID(uid).size()%></li>
        <li>Quizzes Made: <%=Quiz.searchByUserID(uid).size()%></li>
        <li>Admin: <%=User.searchByID(uid).get(0).admin%></li>
        <li>Number of Friends: <%=Friend.getFriends(uid).size()%></li>

      </ul>
    </div>
    <div>
    <% // for friend status information
      assignment.User u = assignment.User.searchByUsername(name).get(0);
      int user_id = (Integer) request.getSession().getAttribute("uid"); 
      String currentUser = (String) request.getSession().getAttribute("username");
   	  int status = assignment.Friend.getStatusOfFriendship(user_id, u.user_id);
   	  if(!name.equals(currentUser)) {%>
   	  <form id="block-form" method="post" action="/DreamMachine/block">
   	  		<input type="hidden" name="name" value="<%=name%>">
   	  		<input type="submit" value="block">
   	  </form><%
   	  if(status == assignment.Friend.NONE) {%>
   	  	<form id="friend-request-form" method="post" action="/DreamMachine/friendRequest">
   	  		<input type="hidden" name="name" value="<%=name%>">
   	  		<input type="submit" value="add friend">
   	  	</form>
   	  <%} else if(status == assignment.Friend.ACCEPTED) {%>
   	  	<form id="unfriend" method="post" action="/DreamMachine/unfriend">
   	  		<input type="hidden" name="name" value="<%=name%>">
   	  		<input type="submit" value="unfriend">
   	  	</form> 
   	  	<form id="challenge" method="post" action="/DreamMachine/challengeRequest">
   	  		<input type="hidden" name="name" value="<%=name%>">
   	  		<input type="submit" value="challenge them!">
   	  	</form> 
   	  <%}}%>
   	  </div>
  </div>
</div>