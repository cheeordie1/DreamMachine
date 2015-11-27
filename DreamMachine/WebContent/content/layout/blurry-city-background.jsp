<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/pic-background-layout.css">
<div id="city-bg-container">
  <%
    java.util.Random rgen = (java.util.Random) request.getServletContext().getAttribute("rint");
    int r_city = rgen.nextInt(5) + 1;
  %>
  <img src=<%= "/DreamMachine/assets/images/city" + r_city + ".jpg" %> id="background-img">
</div>