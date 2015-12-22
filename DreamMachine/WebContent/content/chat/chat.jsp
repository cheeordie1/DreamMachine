<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/chat.css">
<link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Vollkorn' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="http://dreammachine-chat.herokuapp.com/faye/client.js"></script>
<script src="/DreamMachine/assets/javascripts/chat.js"></script>
<%@ page import="java.util.List,assignment.*" %>
<script>
  var co = new ChatOpener("<%= request.getSession().getAttribute("username").toString() %>");
  <%
    List<User> friendUsers = User.searchByUsernameFriends(request.getSession().getAttribute("username").toString(), false);
    for (User user : friendUsers) {
  %>
    co.addOpener("<%= user.username %>", "<%= "/DreamMachine/image/" + user.photo_id %>");
  <% } %>
</script>