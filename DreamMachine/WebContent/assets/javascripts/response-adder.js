function responseAdder (answerContainerID, numAnswersID, adderButtonID)
{
	var obj = this;
	obj.answerContainerID = "#" + answerContainerID;
	obj.numAnswersID = "#" + numAnswersID;
	obj.adderButtonID = "#" + adderButtonID;
	obj.numAnswers = 0;
	
	obj.addAnswer = function() {
		var newAnswerInput;
		obj.numAnswers++;
		$(obj.numAnswersID).val (obj.numAnswers);
		newAnswerInput = $("<input type='text' class='response-input hori-center'>");
		newAnswerInput.attr ("id", "answer" + obj.numAnswers);
		newAnswerInput.attr ("name", "answer" + obj.numAnswers);
		$(obj.answerContainerID).append ($(newAnswerInput));
	}
	
	$(obj.adderButtonID).on ("click", obj.addAnswer);
	
	obj.addAnswer ();
}