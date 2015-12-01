<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/user-sidebar.css">
<nav id="left" class="column">
    <%@ page import="assignment.User, java.util.List" %>
    <% 
	 	 String username = (String) request.getSession().getAttribute("username");
  	 	 int uid = (int) request.getSession().getAttribute("uid");
  		 List<User> userList = User.searchById(uid);
  		 User user = userList.get(0);
    %>
	<h3>Welcome  <%=user.username%> ></h3>
	
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