<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/client-chat.css">
<script type="text/javascript" src="http://localhost:8123/faye/client.js"></script>
<%@ page import="java.util.List, java.util.ArrayList,assignment.*" %>
<script>
	<%
	/* whenever we return home, there are a handful of things to do 
		1.) write all the friends to the chat panel 
		2.) write all pending friend requests to the request panel
		2.) search the database for any chats received while offline 
		3.) always check pending requests 
	*/

	/* global variables */
	int userID = Integer.parseInt(request.getSession().getAttribute("uid").toString());
	List<User> users = User.searchByID(userID);
	User user = users.get(0);
				
	/* this user's friends */
	List<Friend> userFriends = Friend.searchByUserID(userID);
			
	/* set up friend chats */
	List<User> userFriendsAsUsers = new ArrayList<User>();
	if(userFriends != null) {
		for(Friend friend : userFriends) {
			int friendUserID = friend.sender == userID ? friend.receiver : friend.sender;
			user = assignment.User.searchByID(friendUserID).get(0);
			userFriendsAsUsers.add(user);
			%>register_chat('<%=user.username%>');<%
		}
	}%>
		
	var cur_chat = null;

	/* set up faye subscriptions for messages and friend/challenge requests*/
	var client = new Faye.Client('http://localhost:8123/faye', {timeout: 20});
	<%String uname =  (String) request.getSession().getAttribute("username");%>
	
	/* chat subscription */	
	client.subscribe('/'+'<%=uname%>', function(message) {
		var sender = message.substring(0,message.indexOf(' ')-1);

		/* add the message to the chat box */
		var id = sender+'popup-messages';
		display_message(id, message, 'black');
			
		/* add the message to the session cache */	
		/* parse the message */
		if(sender != cur_chat)
			update_sidebar_entry_by_name(sender, "red", "bold");
	});
	
	/* friend request subscription */
	client.subscribe('/'+'<%=uname%>'+ '/' + 'requests', function(message) {
		var sender = message.substring(0,message.length);
		
		/* check it's not contained */
		
		/* add friend request to panel */
		var requests = document.getElementById('friend-requests');
		var new_request = '<div class="friend-requests">' +
			'<div class="sidebar-name">' +
				'<a id="friend-response-link" class="response-link" \
				href=" /DreamMachine/friendResponse/'+sender+'">'+
					'<span>' + sender + '</span>' +
				'</a>' +
			'</div>' +
		'</div>'
		
		requests.innerHTML = requests.innerHTML + new_request;
		
		/* add to request list using ajax*/
		
	});
	
	
	
	
  	function open_chat(friend) {
  		/* close existing chat */
  		if(cur_chat) close_chat();
  			
  		/* in case there are pending messages, write them out 
  			and then remove them */
  		update_sidebar_entry_by_name(friend, "black", "normal");
  			
  		/* assign new current chat */
  		cur_chat = friend;
  		var elem = document.getElementById(cur_chat+'container');
  		elem.style.display = "block";
  	};
  		
  	function close_chat() {
  		/* close current chat popup */
  		var element = document.getElementById(cur_chat+'container');
  		element.style.display = "none";
  		cur_chat = null;
  	};
  		
  	function register_chat(friend) {
  		var element = '<div class="box-container" id="'+friend+'container'+'">';
  		element = element + '<div class="popup-box chat-popup" id="'+ friend +'">';
  		element = element + '<div class="popup-head">';
  		element = element + '<div class="popup-head-left">'+ friend +'</div>';
  		element = element + 
  			'<div class="popup-head-right"><a href="javascript:close_chat();">&#10005;</a></div>';
  		element = element + 
  			'<div style="clear: both"></div></div><div class="popup-messages" id="'+
  			friend+'popup-messages'+'"></div></div>';
  		
  		/* add the chat bar with text area and form */
  		var text_area = '<div class="textarea" id="'+friend+'textarea'+'"> \
  			<textarea name="styled-textarea" id="styled"></textarea></div>';
  		var send_button = '<div class="send-button"> \
  			<a href="javascript:send_chat(\''+friend+'\');">:)</a></div>';
  	 	element = element + '<div class="chat-bar">'+
  	 		text_area + 
  	 		send_button +
  	 	'</div>';
  	 		
  	 	/* populate this chat with messages from the map */
  	 	
  	 	$.ajax ({
  	 		type: 'GET',
  	 		url: '/DreamMachine/message',
  	 		data: { message : friend },
  	 		datatype: 'json',
  	 		success: function(result) {
  	 			if(result != null) {
  	 				var id = friend+'popup-messages';
  	 				$.each(result, function(index, element) {
  	  	 				display_message(id, element.message, 'black');
  	 				});
  	 			}
  	 		}
  	 	});
  	 	
  		$("body").append($(element));
	}
	
	
		
	function send_chat(friend) {
		/* get the message from the chat box of the current user*/
		var message = document.getElementById(friend+'textarea').lastChild.value;
			
		/* put the message in the chat box */
		message = '<%=uname%>' + ': '+ message;
		
		/* store the message in the db */
		$.ajax({
			type: 'POST',
			url: '/DreamMachine/message',
			data: { message : message, username: friend }
		});
		
		/* send the message instantly */
		client.publish('/'+friend, message);
		var id = friend+'popup-messages';
		display_message(id, message, '#9266BD');
			
		/* set the chatbox to empty */
		document.getElementById(friend+'textarea').lastChild.value = "";
	}

	function display_message(id, message, color) {
		var chat = document.getElementById(id);
		var len = message.length;
		chat.innerHTML = chat.innerHTML + '<p style="color:'+color+'">';
		while (len - 35 > 0) {
			var partition = message.substring(0, 35);
			chat.innerHTML = chat.innerHTML + partition + '</br>';
			len = len - 35;
			message = message.substring(35);
		}
		chat.innerHTML = chat.innerHTML + message + '</p>';
	}

	function update_sidebar_entry_by_name(name, color, weight) {
		var slidebar_names = document.getElementsByClassName("sidebar-name");
		for ( var idx in slidebar_names) {
			var s = slidebar_names[idx].getElementsByTagName("span")[0].innerHTML;
			if (s === name) {
				var a = slidebar_names[idx].getElementsByTagName("a")[0];
				a.style.color = color;
				a.style.fontWeight = weight;
				break;
			}
		}
	}
</script>
<div class="sidebar-container">
  <div class="friend-requests" id="friend-requests"></div>
  <div class="chat-sidebar">
  <%for(User userFriendAsUser : userFriendsAsUsers) {%>
	<div class="sidebar-name">
	<a href="javascript:open_chat('<%=userFriendAsUser.username%>')"> <span><%=userFriendAsUser.username%></span></a>
	</div>
  <%}%>
  </div>
</div>
<script>
<%
List<Friend> pendingRequests = Friend.searchByReceiverIDStatus(user.user_id, Friend.PENDING);
for(Friend pendingRequest : pendingRequests) {
	User requestingUser = User.searchByID(pendingRequest.sender).get(0);
	String requestingName = requestingUser.username;%>
	
	var friend_bar = document.getElementById('friend-requests');
	friend_bar.innerHTML = friend_bar.innerHTML +
		'<div class="sidebar-name">' +
			'<a id="friend-response-link" class="response-link" href="/DreamMachine/friendResponse/' + '<%=requestingName%>' + '">' + 
			'<span>' + '<%=requestingName%>' + '</span>' + 
			'</a>' +
		'</div>';
<%}%>
</script>