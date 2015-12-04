<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <%@ page import="assignment.Friend" %>

  <%
  int uid =  Integer.valueOf(request.getParameter("user_id"));
  User user = User.searchByID(uid).get(0);
  List<Integer> friendIDs = Friend.getFriends(uid);
  %>

  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/all-friends.css">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <title><%=user.username%> + "'s Friends"</title>
  <%@ page import="assignment.User, java.util.List, assignment.Photo" %>
</head>
<body>
  <div id="friends-sect">
    <h3>Your Friends</h3>
    <div id="friends-sect-cont" class="well">
	    <table class="table">
	      <thead>
            <tr>
              <th></th>
              <th></th>
            </tr>
          </thead>
	      <tbody>
	        <%for (int i = 0; i < friendIDs.size(); i++) { %>
	        <tr>
	           <% 
	            User friend = User.searchByID(friendIDs.get(i)).get(0); 
	            Photo photo = Photo.searchById(friend.photo_id).get(0);
	            %>
	          <td>
	            <img src=<%=photo.toString()%>>
	          </td>
	          <td>
	            <a href="/DreamMachine/User/<%=friend.user_id%>">
	            <%= User.searchByID(friendIDs.get(i)).get(0).username %>
	            </a>
	          </td>
	        </tr>
	        <%} %>
	      </tbody>
	    </table>
    </div>
  </div>			 
</body>
</html>

