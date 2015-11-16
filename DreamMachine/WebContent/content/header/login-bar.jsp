<link rel="stylesheet" type="text/css" href="assets/stylesheets/login-bar.css">
<div id="login-bar-container" class="vert-center">
      <%
        HttpSession sess = request.getSession();
        if (sess.getAttribute("loggedIn").toString().equals("true")) {
      %>
      <div id="logged-in-text">
        Hello, <%= sess.getAttribute("username") %><br>
        <div id="logout-options">
          <a id="account-link" class="login-link" href=<%= "/user/" + sess.getAttribute("username") %> >Account</a>
          <div class="login-separator">|</div>
          <a id="logout-link" class="login-link" href="/DreamMachine/logout">Logout</a>
        </div>
      </div>
      <% } else { %>
      <div id="not-logged-in-text">
        <a id="login-link" class="login-link" href="/DreamMachine/login">Login</a>
        <div class="login-separator">|</div>
        <a id="signup-link" class="login-link" href="/DreamMachine/signup">Sign Up</a>
      </div>
      <% } %>
</div>