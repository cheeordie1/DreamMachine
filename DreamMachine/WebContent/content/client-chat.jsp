<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Chat</title>
</head>

<%
  	/* add the friends using Betsy's code */
  	//java.util.ArrayList<Integer> friends = new java.util.ArrayList<Integer>();
  	
  	// username of current open chat
  	
  	  	
  	/* but for now, do it by username */
  	java.util.ArrayList<String> friends = new java.util.ArrayList<String>();
	String name = (String) request.getSession().getAttribute("username");
	if(name != null && name.equals("james"))
		friends.add("monkey");
	else
		friends.add("james");
	
	friends.add("winner");
	friends.add("loser");
	friends.add("sex");
	friends.add("fuck");
	friends.add("boop");
	friends.add("beep");
	friends.add("baap");
	friends.add("buup");
	friends.add("byyp");
	friends.add("biip");
	
	/* cache for messages */
	java.util.HashMap<String, java.util.ArrayList<String>> messages = 
		new java.util.HashMap<String, java.util.ArrayList<String>>();
	for(String friend : friends) {
		messages.put(friend, new java.util.ArrayList<String>());
		
	}
 	 %>


<script type="text/javascript"
	src="http://localhost:8123/faye/client.js">
    </script>

<script>
		var cur_chat = null;
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
  			var text_area = '<textarea name="styled-textarea" id="styled">Chat ...</textarea>';
  			var send_button = '<div class="send-button"><a href="javascript:send_chat();">-></a></div>';
  	 		element = element + '<div class="chat-bar">'+
  	 			text_area + 
  	 			send_button +
  	 		'</div>';
  	 		
  			document.getElementsByTagName("body")[0].innerHTML = 
  				document.getElementsByTagName("body")[0].innerHTML + element; 
		}
</script>


<script>
	<%for(String friend : friends) {%>
		register_chat('<%=friend%>');
	<%}%>
</script>

<div class="chat-sidebar">
	<%for(String friend : friends) {%>
	<div class="sidebar-name">
		<a href="javascript:open_chat('<%=friend%>')"> <span><%=friend%></span>
		</a>
	</div>
	<%}%>
</div>


<script>
		var client = new Faye.Client('http://localhost:8123/faye', {timeout: 20});
		<%String uname = "/" + (String) request.getSession().getAttribute("username");%>
		client.subscribe('<%=uname%>', function(message) {
			/* parse the message */
			var sender = message.substring(0,message.indexOf(' ')-1);
			if(sender != cur_chat) {
				/* signal to the client they have a pending message and 
					add it to the cached message map */
				update_sidebar_entry_by_name(sender, "red", "bold");
			} else {
				document.getElementById(sender+'popup-messages').innerHTML =
					document.getElementById(sender+'popup-messages').innerHTML + message+"<br>";
				var m = document.getElementById(sender+'popup-messages');
			}			
		});
</script>

<script>
		function send() {
			[SomeTextarea].onkeyup = function(e){
				  e = e || event;
				  if (e.keyCode === 13 && !e.ctrlKey) {
				    // start your submit function
				  }
				  return true;
				 }
		}
		
		
		
		function update_sidebar_entry_by_name(name, color, weight) {
			var slidebar_names = document.getElementsByClassName("sidebar-name");
			for(var idx in slidebar_names) {
				var s = slidebar_names[idx].getElementsByTagName("span")[0].innerHTML;
				if(s === name) {
					var a = slidebar_names[idx].getElementsByTagName("a")[0];
					a.style.color = color;
					a.style.fontWeight = weight;
					break;
				}
			}
		}
</script>


<body>
</body>
</html>