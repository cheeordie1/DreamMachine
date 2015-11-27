<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/profile-box.css">
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<div id="profile-container" class="hori-center">
  <div id="profile-photo-container">
    <%
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
        PUT QUIZ STATS IN HERE e.g.:
        <li># quizzes taken</li>
        <li># quizzes made</li>
        <li>Account status (moderator or not)</li>
        <li>number of friends</li>
        <li>number of perfect quizzes</li>
      </ul>
    </div>
  </div>
</div>