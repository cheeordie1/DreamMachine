function TagBox(containerID, numTagsID) {
	this.containerID = "#" + containerID;
	this.numTagsID = "#" + numTagsID;
	this.numTags = 0;
	$(this.numTagsID).val(numTags);
	var obj = this;
	
	this.addTagEnter = function(evt) {
		if (evt.keyCode == 13) {
			evt.stopPropagation();
			obj.addTag(evt);
		}
	}
	
	this.addTag = function(evt) {
		var tagVal = $("#tagger").val();
		if (!tagVal.match(/\s/g) && tagVal) {
		  numTags++;
		  $(obj.containerID).append("<div id=tag" + obj.numTags + " class=tag name=tag" + obj.numTags +
				                     "><input type='hidden' name=tag" + obj.numTags + " value=" +
				                     tagVal + "></input>" + tagVal + "</div>");
		  $(obj.numTagsID).val(numTags);
		}
		$(document).unbind("keypress", obj.addTagEnter);
		$(document).unbind("click", obj.addTag);
		$("#tagger-container").remove();
		$(obj.containerID).bind("click", obj.clickTag);
	}
	
	this.stopRemoval = function(evt) {
		evt.stopPropagation();
	}
	
	this.clickTag = function(evt) {
		$(obj.containerID).append("<div id='tagger-container' class='tagger-container'><input id='tagger' class='tagger'></input><br>Add a single word tag</div>");
		$("#tagger").select();
		$(obj.containerID).unbind("click", obj.clickTag);
		setTimeout (function() {
			$(document).bind("click", obj.addTag);
			$(document).bind("keypress", obj.addTagEnter);
		}, 200);
		$("#tagger-container").bind("click", obj.stopRemoval);
	};
	
	$(this.containerID).bind("click", obj.clickTag);
};