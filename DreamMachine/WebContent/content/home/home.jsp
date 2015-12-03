
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/home.css">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <jsp:include page="/content/app.jsp" />
  <title>Welcome to Dream Machine</title>
  <%@ page import="assignment.User, java.util.List" %>
  
  
</head>
<body>

<script>
	function divClicked() {
		$(this).html("Clicked");
	//Do form submission
	}

	$(function() {
    	$("input[type=submit]").addClass("hidden");
	});
</script>
	
    <%-- 
	<div class="container">
	
		<div class = "row">
			<div class="col-xs-12 col-sm-6 col-md-8 col-md-offset-4">
				<jsp:include page="../header/top-bar.jsp" />
			</div>
		</div>
	
		<div class="row">
		
		  <div class="col-md-4" id = "left-sidebar-homepage">
		  	<jsp:include page="../left-sidebar/user-sidebar.jsp" />
		  	
		  </div>
		  
		  <div class="col-md-4">
		  	<jsp:include page="../content/main-content.jsp" />
		  </div>
		  	
		  <div class="col-md-4">
		  	<jsp:include page="../header/feed-sidebar.jsp" />
		  </div>
		  
		</div>
		
	</div>
		 --%>
		<%--  <jsp:include page="/content/client-chat.jsp" />  --%>
		
		<div id="wrapper">
		    <div id="sidebar-wrapper">
		        <div class="sidebar-nav">
		            <jsp:include page="../left-sidebar/user-sidebar.jsp" />
		        </div>
		    </div>
		    <div id="page-content-wrapper">
		        <div class="page-content">
		            <div class="container">
		            	<div class = "row">
		            		<div id = "search-bar-back">
			            		<div class = "col-md-12" id = "search-bar">
			            			<form method="post" action="/DreamMachine/search">
								        <input id="search-term" type="text" name="term" class="form-control" placeholder="Search for Quizzes and Users..." >
								        <input type="submit" style="display: none;"/>
								    </form>
			            		</div>
			            	</div>
		            	</div>
		                <div class="row">
		                    <div class="col-md-12" id ="whatami">
		                        <%-- <jsp:include page="../content/main-content.jsp" /> --%>
		                        
		                        <%-- TODO: Populate this with announcements --%>
		                        <div id="pop-quizzes-sect">	
								    <h3>Announcements</h3>
								    <div id="pop-quizzes-cont" class="well">
									  <table class="table table-condensed">
									    <tbody>
									      <tr>
									        <td>New Quiz Created!! take it now!</td>
									      </tr>
									    </tbody>
									  </table>
								    </div>
								  </div>
								
								<div id="pop-quizzes-sect">	
								    <h3>Popular Quizzes</h3>
								    <div id="pop-quizzes-cont" class="well">
									  <table class="table table-condensed">
									    <tbody>
									      <tr>
									        <td>How Deep is Your Love by BeeJee</td>
									      </tr>
									      <tr>
									        <td>How much wood and other crazy questions by bobby</td>
									      </tr>
									      <tr>
									        <td>2 for 1 by bobby brown</td>
									      </tr>
									    </tbody>
									  </table>
								    </div>
								  </div>
								  
								  
								  <%-- TODO: Populate this with recent quizzes --%>
								  <div id="recent-quizzes-sect">
								    <h3>Recent Quizzes</h3>
								    <div id="recent-quizzes-cont" class="well">
								      
									    <table class="table table-condensed">
									      <tbody>
									        <tr>
									          <td>How Deep is Your Love by BeeJee</td>
									        </tr>
									        <tr>
									          <td>How much wood and other crazy questions by bobby</td>
									        </tr>
									        <tr>
									          <td>2 for 1 by bobby brown</td>
									        </tr>
									      </tbody>
									    </table>
								    </div>
								  </div>			                    
		                    
		                    
		                    	<%-- <jsp:include page="../header/feed-sidebar.jsp" /> --%>
		                    	
		                    	<%-- TODO: Populate this with friends activity --%>
		                    	<div id="friend-activity-sect">	
								    <h3>Popular Quizzes</h3>
								    <div id="friend-activity-content" class="well">
									  <table class="table table-condensed">
									    <tbody>
									      <tr>
									        <td>Sage just played a game!</td>
									      </tr>
									    </tbody>
									  </table>
								    </div>
								  </div>
								  
								  
								  <%-- TODO: Populate this with recent general activity --%>
								  <div id="general-activity-sect">
								    <h3>Recent Quizzes</h3>
								    <div id="general-activity-content" class="well">
								      
									    <table class="table table-condensed">
									      <tbody>
									        <tr>
									          <td>Stranger is online!</td>
									        </tr>
									      </tbody>
									    </table>
								    </div>
								  </div>	
		                    </div>
		                    </div>
		                    
		                    
		                    
		                    
		                    
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
	
</body>

</html>


<!-- %@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/home.css">
<jsp:include page="/content/app.jsp" />
<script src="jquery.js"></script> 
<title>Welcome to Dream Machine</title>
</head>
<body>
  <jsp:include page="../header/top-bar.jsp" />
  <div id="includedContent"></div>
  <div id="home-div">
    <jsp:include page="words.jsp" />
    <div id="leaderboards-div">
      <div id="top-played-container">
        <jsp:include page="top-played.jsp" />
      </div>
      <div id="most-quizzes-container">
        <jsp:include page="most-quizzes.jsp" />
      </div>
      <div id="recently-created-container">
        <jsp:include page="recently-created.jsp" />
      </div>
    </div>
  </div>
  <jsp:include page="/content/client-chat.jsp" />
</body>
</html-->
