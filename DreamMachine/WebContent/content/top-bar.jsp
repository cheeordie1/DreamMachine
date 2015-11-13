<link rel="stylesheet" type="text/css" href="assets/stylesheets/top-bar.css">
<div id="top-bar-container">
  <div id="logo">
    <a id="logo-link" href="/DreamMachine/home">
      <img id="logo-img" src="assets/images/logo.png">
    </a>
  </div>
  <jsp:include page="search-bar.jsp">
    <jsp:param value="" name=""/>
  </jsp:include>
  <jsp:include page="login-bar.jsp">
    <jsp:param value="" name=""/>
  </jsp:include>
</div>