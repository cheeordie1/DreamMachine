<link rel="stylesheet" type="text/css" href="assets/stylesheets/login-bar.css">
<div id="login-bar-container" class="vert-center">
    <div id="logged-in-text">
      <%
        HttpSession sess = request.getSession();
        if (sess.getAttribute("loggedIn").toString().equals("true")) {
      %>
        Hello, <%= sess.getAttribute("username") %><br>
        <a id="account-link" href=<%= "/user/" + sess.getAttribute("username") %> >Account</a>
      <% } else { %>
        <a id="login-link" class="login-link" href="/DreamMachine/login">Login</a>
        <div id="login-separator">|</div>
        <a id="signup-link" class="login-link" href="/DreamMachine/signup">Sign Up</a>
      <% } %>
    </div>
</div>