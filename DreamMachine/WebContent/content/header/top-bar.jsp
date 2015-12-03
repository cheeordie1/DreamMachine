<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/top-bar.css">
<div id="top-bar-container">

	  <form method="post" action="/DreamMachine/search">
	    <input id="search-term" type="text" name="term" class="form-control" placeholder="Search for Quizzes and Users...">
	    <input type="submit" style="display: none;"/>
	  </form>
	
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
  <jsp:include page="/content/header/search-bar.jsp" />
  <jsp:include page="/content/header/create-quiz-button.jsp" /> --%>
  
</div>