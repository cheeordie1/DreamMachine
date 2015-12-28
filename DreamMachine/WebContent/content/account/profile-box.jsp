<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/profile-box.css">
<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
<%@ page import="java.util.List,assignment.*" %>
<div id="profile-container" class="hori-center">
  <div id="profile-photo-container">
    <%
      User pageUser = (User) request.getAttribute("pageUser");
      java.util.List<assignment.Photo> photos = assignment.Photo.searchById(pageUser.photo_id);
      assignment.Photo photo = photos.get(0);
      String imgStyle = photo.height > photo.width ? "height:300px" : "width:300px";
    %>
    <img id="profile-photo" class="hori-vert-center" style=<%= imgStyle %> src=<%= "/DreamMachine/image/" + pageUser.photo_id %>>
  </div>
  <% 
    String name = pageUser.username; 
    int fontSize = name.length() > 22 ? (name.length() * 30 / 22) : 30;
  %>
  <div id="profile-name" style=<%= "font-size:" + fontSize  + "px" %>><%= pageUser.username %></div>
  <div id="profile-info-container">
    <jsp:include page="/content/account/profile-stats.jsp" />
  </div>
</div>