<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/pic-background-layout.css">
<div id="galaxy-bg-container">
  <%
    java.util.Random rgen = (java.util.Random) request.getServletContext().getAttribute("rint");
    int r_lights = rgen.nextInt(4) + 1;
  %>
  <img src=<%= "/DreamMachine/assets/images/lights" + r_lights + ".jpg" %> id="background-img">
</div>