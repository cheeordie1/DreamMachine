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
  this.chatContainer = ;
  this.count = 0;
  var obj = this;
  
  this.addChat = function(receiverName) {
    if ($("#" + receiverName + "-chat").length == 0) return;
    var displayID, inputID, sendButtonID;
    displayID = receiverName + "-chat-display";
    inputID = receiverName + "-chat-input";
    sendButtonID = receiverName + "-chat-button";
    
    new Chat(displayID, inputID, sendButtonID, obj.senderName, receiverName);
    obj.count++;
  }	
}

function Chat(displayID, inputID, sendButtonID, sender, receiver)
{
  this.displayID = "#" + displayID;
  this.inputID = "#" + inputID;
  this.sendButtonID = "#" + sendButtonID;
  this.sender = sender;
  this.receiver = receiver;
  this.numMessages = 0;
  this.client = new Faye.Client ("http://dreammachine-chat.herokuapp.com/faye");
  var obj = this;
  
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
  
  $(obj.sendButtonID).on ("click", obj.sendMessage);
  $(obj.inputID).on ("keypress", obj.sendMessageOnKey);
}