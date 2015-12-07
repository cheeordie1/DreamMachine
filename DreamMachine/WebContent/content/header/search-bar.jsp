<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/search-bar.css">
<script src="/DreamMachine/assets/javascripts/form_button.js"></script>
<div id="search-bar-container" class="hori-vert-center">
  <div id="search-bar-button-container">
    <form id="search-bar-form" method="post" action="/DreamMachine/search">
      <input id="search-term" type="text" name="term">
      <div id="search-options-container">
        <div id="search-quiz-options-container" class="search-by-option-container">
          <label class="search-by-label">Search for Quiz</label>
          <input type="radio" name="search-by" id="search-by-quiz-radio" class="search-by-radio" value="quiz" checked="checked" />
  		  <div id="search-quiz-sub-options-container">		     
  		    <label id="search-by-tag-sub-label" class="search-by-sub-label">by tag</label>
            <input type="checkbox" id="search-by-tag" class="search-by-sub-checkbox" name="by-tag" value="tag" />  		  
  		    <label id="search-by-creator-sub-label" class="search-by-sub-label">by creator</label>
            <input type="checkbox" id="search-by-creator" class="search-by-sub-checkbox" name="by-creator" value="creator" />  		    
  		  </div>
        </div>
        <div id="search-user-options-container" class="search-by-option-container">
          <label class="search-by-label">Search for User</label>
          <input type="radio" name="search-by" id="search-by-user-radio" class="search-by-radio" value="name" />
          <div id="search-user-sub-options-container">
            <label class="search-by-sub-label">friends only</label>   
            <input type="checkbox" id="search-by-friends" class="search-by-sub-checkbox" name="friends-only" value="friends-only" />
          </div>
        </div>
      </div>
    </form>
    <img id="search-button" src="/DreamMachine/assets/images/search_button_normal.png" />
    <div class="preload"><img src="/DreamMachine/assets/images/search_button_highlight.png" height="1px" width="1px" /></div>
    <div class="preload"><img src="/DreamMachine/assets/images/search_button_selected.png" height="1px" width="1px" /></div>
  </div>
  <script>
    new formButton ("search-bar-form", "search-button", 
		  			"/DreamMachine/assets/images/search_button_normal.png",
		  			"/DreamMachine/assets/images/search_button_highlight.png",
		  			"/DreamMachine/assets/images/search_button_selected.png");
  </script>
</div>