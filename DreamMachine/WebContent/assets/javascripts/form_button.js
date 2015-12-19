function formButton(formID, buttonID, normalURL, highlightURL, selectedURL) {
	var obj = this;
	obj.formID = formID == null ? null : ("#" + formID);
	obj.buttonID = "#" + buttonID;
	obj.normalURL = normalURL;
	obj.highlightURL = highlightURL;
	obj.selectedURL = selectedURL;
	
	$(obj.buttonID).addClass ("undraggable");
	
	if (obj.formID != null)
	  {
	    $(obj.buttonID).on ("click", function (evt)
	      {
	        $(obj.formID).submit ();
	      });
	  }
	
	$(obj.buttonID).on ("mouseover", function (evt)
	  {
		$(obj.buttonID).attr ("src", obj.highlightURL);
	  });
	
	$(obj.buttonID).on ("mouseout", function (evt)
	  {
	    $(obj.buttonID).attr ("src", obj.normalURL);
	  });
	
	$(obj.buttonID).on ("mousedown", function (evt)
	  {
		$(obj.buttonID).attr ("src", obj.selectedURL);
	  });
}