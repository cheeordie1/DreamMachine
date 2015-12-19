function ChatOpener(receiverName, openerID, factory)
{
  this.receiverName = receiverName;
  this.openerID = "#" + openerID;
  this.factory = factory;
  var obj = this;
  
  $(this.openerID).on("click", function(evt) {
    obj.factory.addChat(obj.receiverName);
  });
}

function ChatCloser(closerID, factory)
{
  this.closerID = "#" + closerID;
  this.factory = factory;
  var obj = this;
  
  $(this.closerID).on("click", function(evt) {
	obj.factory.count--;
  })
}

function ChatFactory(senderName) 
{
  this.senderName = senderName;
  this.chatArray = [];
  this.$chatContainer = $("<div>", { id: "chat-container" });
  $("body").append (this.$chatContainer);
  this.count = 0;
  var obj = this;
  
  obj.addChat = function(receiverName) {
	for (var tuple in obj.chatArray)
	  if (tuple[0].valueOf () == receiverName.valueOf()) return;
	obj.chatArray.push([receiverName, null]);
    var displayID, inputID, sendButtonID, exitButtonID;
    displayID = receiverName + "-chat-display";
    inputID = receiverName + "-chat-input";
    sendButtonID = receiverName + "-chat-button";
    exitButtonID = receiverName + "-chat-exit-button";
    
    // add chat html to chat container
    var $chatBoxContainer = $("<div>", { id: receiverName + "-chat-box-container", class: "chat-box-container" });
    var $chatBoxTopBar = $("<div>", { class: "chat-box-top-bar" });
    $chatBoxTopBar.append ($("<span>" + receiverName + "</span>", { class: "chat-box-top-bar-name" }));
    var $chatBoxExit = $("<img src='' class='chat-box-exit-button'>");
    $chatBoxTopBar.append ($chatBoxExit);
    var $chatBoxMessageBar = $("<div>", { class: "chat-box-message-bar" });
    $charBoxMessageBar.append ($("<input>", { type: "text" , id: inputID , class: "chat-box-input" }));
    $
    $chatBoxContainer.append ($chatBoxTopBar);
    $chatBoxContainer.append ($("<div>"), { id: displayID });
    $chatBoxContainer.append ($chatBoxMessageBar);
    
    // add chat box to factory array
    obj.chatArray[count][1] = $chatBoxContainer;
    
    new Chat(obj, displayID, inputID, sendButtonID, exitButtonID, obj.senderName, receiverName);
    obj.count++;
  };
  
  obj.removeChat = function(receiverName) {
	obj.chatArray.forEach (function (val, idx)
	  {
	    if (val[0].valueOf() == receiverName.valueOf())
	      {
	    	obj.chatArray.splice(idx, 1);
	        obj.count--;
	      }
	  });
  };
  
}

function Chat(factory, displayID, inputID, sendButtonID, exitButtonID, sender, receiver)
{
  this.factory = factory;
  this.displayID = "#" + displayID;
  this.inputID = "#" + inputID;
  this.sendButtonID = "#" + sendButtonID;
  this.exitButtonID = "#" + exitButtonID;
  this.sender = sender;
  this.receiver = receiver;
  this.numMessages = 0;
  this.client = new Faye.Client ("http://dreammachine-chat.herokuapp.com/faye");
  var obj = this;
  
  obj.exitChat = function(evt) {
	obj.factory.removeChat(receiver);
  };
  
  obj.addMessage = function(senderName, msg) {
	var $listElem = $("<li>", { id: "chat-msg-" + obj.numMessages, class: "chat-msg" });
	$listElem.append ($("<span>" + senderName + "</span>", { class: "chat-msg-name" }));
	$listElem.append ($("<span>:</span>", { class: "chat-msg-separator" }));
	$listElem.append ($("<span>" + msg + "</span>", { class: "chat-msg-text" }));
	obj.numMessages++;
	$(obj.displayID).append ($listElem);
  }
  
  obj.sendMessage = function(evt) {
	var text = $(obj.inputID).val ();
	if (/\S/.test(text) == false) return;
	$(obj.inputID).val ("");
	obj.addMessage (sender, text);
	obj.client.publish ('/' + receiver, JSON.stringify ({
	  sender: sender,
	  receiver: receiver,
	  message: text
	}));
  };
  
  obj.sendMessageOnKey = function(evt) {
	if (evt.keycode == 13) {
	  obj.sendMessage (evt);
	}
  }
  
  $(obj.exitButtonID).on ("click", obj.exitChat);
  $(obj.sendButtonID).on ("click", obj.sendMessage);
  $(obj.inputID).on ("keypress", obj.sendMessageOnKey);
}