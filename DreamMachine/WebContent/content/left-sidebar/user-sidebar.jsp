<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/user-sidebar.css">
<nav id="left" class="column">
    <%@ page import="assignment.DBConnection, java.sql.*, assignment.Photo, assignment.User, java.util.*" %>
	<%  String username = (String) session.getAttribute("username");
		int uid = (int) session.getAttribute("uid");
		List<User> user = User.searchById(uid);
		int photo_id = user.get(0).photo_id;
		Photo photo = Photo.searchById(photo_id);
		String photoSrc = photo.toString();
	%>
	<h3>Welcome <%= getServletContext().getAttribute("username") %> </h3>
	
	<!-- <img id="user-img" src="getServletContext().getAttribute(user" -->
	<img id="user-img" src="/DreamMachine/assets/images/mr-bean.jpeg" alt="user pic"
	class="img-circle">
	
	<br><br><br><br><br><br>
    <img id=mail-img src="/DreamMachine/assets/images/envelope-icon.png"/>
    <br><br><br><br>
    <h5 id="user-history-icon">
      <a href="#">
        your history
      </a>
    </h5>
</nav>