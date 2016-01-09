function ChatOpener(senderName, containerID) {
  this.senderName = senderName;
  this.$chatContainer = $("<div>", { id: "chat-container", class: "chat-container" });
  $("body").append (this.$chatContainer);
  this.factory = new ChatFactory(senderName, this.$chatContainer);
  var obj = this;
  
  obj.$chatOpenerContainer = $("<div>", { class: "chat-opener-container" });
  obj.$chatContainer.append (obj.$chatOpenerContainer);
  obj.$chatOpenerOpener = $("<div>", { class: "chat-opener-opener" });
  obj.$chatOpenerOpener.append ($("<span>", { text: "Chat", class: "chat-opener-title hori-vert-center" }));
  obj.$chatOpenerList = $("<div>", { class: "chat-opener-list" });
  obj.$chatOpenerContainer.append(obj.$chatOpenerOpener);
  obj.$chatOpenerContainer.append(obj.$chatOpenerList);
  
  obj.hideChat = function() {
    if (obj.$chatOpenerContainer.css ("bottom").valueOf() == "77px".valueOf())
      obj.$chatOpenerContainer.css ("bottom", "-254px");
    else
      obj.$chatOpenerContainer.css ("bottom", "77px");
  }
  
  obj.addOpener = function(receiverName, imageURL) {
	var innerObj = obj;
	var name = receiverName;
	var $chatOpenerEntryContainer = $("<div>", { class: "chat-opener-entry-container" });
	obj.$chatOpenerList.append ($chatOpenerEntryContainer);
	$chatOpenerEntryContainer.append ($("<img>", { src: imageURL, class: "chat-opener-entry-img" }));
	$chatOpenerEntryContainer.append ($("<span>", { text: receiverName, class: "chat-opener-entry-name" }));

	$chatOpenerEntryContainer.on ("mouseover", function(evt) {
	  $chatOpenerEntryContainer.css ("background-color", "#B490BC");
	});
	
	$chatOpenerEntryContainer.on ("mouseout", function(evt) {
	  $chatOpenerEntryContainer.css ("background-color", "#FFFFFF");
	});
	
	$chatOpenerEntryContainer.on ("click", function(evt) {
	  innerObj.factory.addChat(receiverName);
	});
  }
  
  obj.$chatOpenerOpener.on ("click", obj.hideChat);
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

function ChatFactory(senderName, chatContainer) 
{
  this.senderName = senderName;
  this.chatArray = [];
  this.$chatContainer = chatContainer;
  this.count = 0;
  var obj = this;
  
  obj.addChat = function(receiverName) {
	var exists = false;
	obj.chatArray.forEach (function (val, idx) {
	  if (val[0].valueOf () == receiverName.valueOf()) exists = true;
	});
	if (exists) return;
	obj.chatArray.push([receiverName, null]);
    var displayID, inputID, sendButtonID, exitButtonID, obj1, hideChat;
    displayID = receiverName + "-chat-display";
    inputID = receiverName + "-chat-input";
    sendButtonID = receiverName + "-chat-button";
    exitButtonID = receiverName + "-chat-exit-button";
    hideChat = function (evt)
      {
    	if ($chatBoxContainer.position ().top > 0)
    	  $chatBoxContainer.css ({ top: 0 });
    	else
    	  $chatBoxContainer.css ({ top: 260 });
      };
    
    // add chat html to chat container
    var $chatBoxContainer = $("<div>", { id: receiverName + "-chat-box-container", class: "chat-box-container" });
    // top bar with name of chatter
    var $chatBoxTopBar = $("<div>", { class: "chat-box-top-bar" });
    $chatBoxTopBar.on ("click", hideChat);
    $chatBoxTopBar.append ($("<span class='chat-box-top-bar-name vert-center'>" + receiverName + "</span>"));
    var $chatBoxExit = $("<img>", { src: "/DreamMachine/assets/images/exit_button_normal.png", id: exitButtonID, class: "chat-box-exit-button vert-center" });
    $chatBoxExit.on("click", function(evt) { obj.removeChat(receiverName); });
    $chatBoxTopBar.append ($chatBoxExit);
    // message display section
    var $chatBoxMessageDisplayContainer = $("<div>", { class: "chat-box-message-display" });
    $chatBoxMessageDisplayContainer.append ($("<ul>", { id: displayID, class: "chat-box-message-display-list" }));
    // message input and send button
    var $chatBoxMessageBar = $("<div>", { class: "chat-box-message-bar" });
    $chatBoxMessageBar.append ($("<input>", { type: "text" , id: inputID , class: "chat-box-input" }));
    $chatBoxMessageBar.append ($("<button>", { id: sendButtonID, class: "chat-box-button", text: "Send" }));
    $chatBoxContainer.append ($chatBoxTopBar);    
    $chatBoxContainer.append ($chatBoxMessageDisplayContainer);
    $chatBoxContainer.append ($chatBoxMessageBar);
    obj.$chatContainer.prepend ($chatBoxContainer);
    
    // add chat box to factory array
    obj.chatArray[obj.count][1] = $chatBoxContainer;
    
    new formButton (null, exitButtonID, "/DreamMachine/assets/images/exit_button_normal.png", 
    		"/DreamMachine/assets/images/exit_button_highlight.png",
	        "/DreamMachine/assets/images/exit_button_selected.png");
    new Chat(obj, displayID, inputID, sendButtonID, exitButtonID, obj.senderName, receiverName);
    obj.count++;
  };
  
  obj.removeChat = function(receiverName) {
	obj.chatArray.forEach (function (val, idx)
	  {
	    if (val[0].valueOf () == receiverName.valueOf ())
	      {
	    	obj.chatArray[idx][1].remove ();
	    	obj.chatArray.splice (idx, 1);
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
	var $listElem = $("<li>", { id: obj.receiver + "-chat-msg-" + obj.numMessages, class: "chat-msg" });
	var nameClass = senderName.valueOf () == obj.sender.valueOf () ? "chat-msg-name-yours" : "chat-msg-name-theirs"
	$listElem.append ($("<span>", { class: nameClass, text: senderName }));
	$listElem.append ($("<span>", { class: nameClass, text: ":", style: "margin-right:5px" }));
	$listElem.append ($("<span>", { class: "chat-msg-text", text: msg }));
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
	if (evt.keyCode == 13) {
	  obj.sendMessage (evt);
	}
  }
  
  $(obj.exitButtonID).on ("click", obj.exitChat);
  $(obj.sendButtonID).on ("click", obj.sendMessage);
  $(obj.inputID).on ("keypress", obj.sendMessageOnKey);
}