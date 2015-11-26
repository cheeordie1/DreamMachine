<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/splatter-background.css">
<div id="splatter-bg-container">
  <%
    java.util.Random rgen = (java.util.Random) request.getServletContext().getAttribute("rint");
    int r_splatter = rgen.nextInt(6) + 1;
  %>
  <img src=<%= "/DreamMachine/assets/images/splatter" + r_splatter + ".jpg" %> id="splatter-img">
</div>