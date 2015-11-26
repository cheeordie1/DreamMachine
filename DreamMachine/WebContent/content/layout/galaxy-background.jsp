<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/galaxy-background.css">
<div id="galaxy-bg-container">
  <%
    java.util.Random rgen = (java.util.Random) request.getServletContext().getAttribute("rint");
    int r_space = rgen.nextInt(5) + 1;
  %>
  <img src=<%= "/DreamMachine/assets/images/space" + r_space + ".jpg" %> id="galaxy-img">
</div>