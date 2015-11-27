$("#create-quiz-button").on ('click', function() {
  window.location.replace ("/DreamMachine/quiz-create");
});

$("#create-quiz-button").on ('mouseover', function() {
  $("#create-quiz-button").attr ("src", "/DreamMachine/assets/images/create_button_highlight.png");
});

$("#create-quiz-button").on ('mouseout', function() {
  $("#create-quiz-button").attr ("src", "/DreamMachine/assets/images/create_button_normal.png");
});