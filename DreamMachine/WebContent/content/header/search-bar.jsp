<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search-bar.css">
<div id="search-bar-container" class="hori-vert-center">
  <form method="post" action="/DreamMachine/search">
    <input id="search-term" type="text" name="term" placeholder=" Search for Quizzes and Users...">
    <input type="submit" style="display: none;"/>
  </form>
</div>

<script>
	function divClicked() {
		$(this).html("Clicked");
	//Do form submission
	}

	$(function() {
    	$("input[type=submit]").addClass("hidden");
	});
</script>
