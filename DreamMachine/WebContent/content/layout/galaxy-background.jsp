<link rel="stylesheet" type="text/css" href="assets/stylesheets/galaxy-background.css">
<div id="galaxy-bg-container">
  <%
    java.util.Random rgen = (java.util.Random) request.getServletContext().getAttribute("rint");
    int r_space = rgen.nextInt(5) + 1;
  %>
  <img src=<%= "assets/images/space" + r_space + ".jpg" %> id="galaxy-img">
</div>