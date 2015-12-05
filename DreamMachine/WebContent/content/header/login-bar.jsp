<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/login-bar.css">
<div id="login-bar-container" class="vert-center">
      <%
        HttpSession sess = request.getSession();
        if ((boolean)sess.getAttribute("loggedIn")) {
      %>
      <div id="logged-in-text">
        Hello, <%= sess.getAttribute("username") %><br>
        <div id="logout-options">
          <a id="account-link" class="login-link" href=<%= "/DreamMachine/user/" + sess.getAttribute("username") %> >Account</a>
          <div class="login-separator">|</div>
          <a id="logout-link" class="login-link" href="/DreamMachine/logout">Logout</a>
          <div class="login-separator">|</div>
          <div class="preload">
   			<img src="/DreamMachine/content/assets/images/create_button_highlight.png" width="1" height="1" />
		  </div>
          <img id="create-quiz-button" src="/DreamMachine/assets/images/create_button_normal.png" />
          <script src="/DreamMachine/assets/javascripts/create_quiz_button.js"></script>
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