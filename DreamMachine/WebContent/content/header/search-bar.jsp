<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search-bar.css">
<script src="/DreamMachine/assets/javascripts/form_button.js"></script>
<div id="search-bar-container" class="hori-vert-center">
  <div id="search-bar-button-container">
    <form id="search-bar-form" method="post" action="/DreamMachine/search">
      <input id="search-term" type="text" name="term">
    </form>
    <img id="search-button" src="/DreamMachine/assets/images/search_button_normal.png" />
    <div class="preload"><img src="/DreamMachine/assets/images/search_button_highlight.png" /></div>
    <div class="preload"><img src="/DreamMachine/assets/images/search_button_selected.png" /></div>
  </div>
  <script>
    new formButton ("search-bar-form", "search-button", 
		  			"/DreamMachine/assets/images/search_button_normal.png",
		  			"/DreamMachine/assets/images/search_button_highlight.png",
		  			"/DreamMachine/assets/images/search_button_selected.png");
  </script>
</div>