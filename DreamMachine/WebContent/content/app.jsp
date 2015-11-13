<link rel="stylesheet" type="text/css" href="assets/stylesheets/app.css">
<link href='https://fonts.googleapis.com/css?family=Arapey|PT+Sans|Archivo+Narrow' rel='stylesheet' type='text/css'>
<% 
  if (request.getSession().getAttribute("loggedIn") == null)
    {
	  request.getSession().setAttribute ("loggedIn", false);
    }
%>