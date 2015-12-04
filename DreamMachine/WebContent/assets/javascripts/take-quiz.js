var sQuiz = function(formID) {
	var obj = this;
		obj.formID = "#" + formID;
		$(obj.formID).on("submit", obj.submitQuestions);
		obj.submitQuestions = function() {
			var cur = 0;
			while($("#q" + cur) != null) {
				$("#q"+cur).submit();
				cur++;
			}
		}

		var Question = function(questionFormID, questionIDX) {
			var obj = this;
			obj.questionFormID = "#" + questionIDX;
			$(obj.questionFormID).submit(obj.submitQuestions);
			
			obj.submitQuestions = function() {
				$("#q" + questionIDX).val("RWW");
			}
		}
}