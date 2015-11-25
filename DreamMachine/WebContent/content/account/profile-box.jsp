<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/profile-box.css">
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<div id="profile-container" class="hori-center">
  <div id="profile-photo-container">
    <img id="profile-photo" class="hori-center" src=<%= "/DreamMachine/image/" + request.getAttribute("profilePic").toString() %>>
  </div>
  <% 
    String name = request.getAttribute("pageUser").toString(); 
    int fontSize = name.length() > 22 ? (name.length() * 30 / 22) : 30;
  %>
  <div id="profile-name" style=<%= "font-size:" + fontSize  + "px" %>><%= request.getAttribute("pageUser").toString() %></div>
  <div id="profile-info-container">
    <div id="profile-info-title" class="profile-info-title hori-center">Info</div>
  </div>
</div>