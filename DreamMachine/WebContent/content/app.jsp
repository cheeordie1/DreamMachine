<link rel="stylesheet" type="text/css" href="assets/stylesheets/app.css">
<% 
  if (request.getSession().getAttribute("loggedIn") == null)
    {
	  request.getSession().setAttribute ("loggedIn", false);
    }
%>