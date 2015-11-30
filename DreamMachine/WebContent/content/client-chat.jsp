<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Chat</title>
</head>

	<script type="text/javascript" 
        	src="http://localhost:8123/faye/client.js">
    </script>
    
	<script>
		var cur_chat = null;
  		function open_chat(friend) {
  			/* close existing chat */
  			if(cur_chat) close_chat();
  			
  			/* setup chat box */
  			var element = '<div class="popup-box chat-popup" id="'+ friend +'">';
 			element = element + '<div class="chat-bar">bar</div>';
  			element = element + '<div class="popup-head">';
  			element = element + '<div class="popup-head-left">'+ friend +'</div>';
  			element = element + 
  				'<div class="popup-head-right"><a href="javascript:close_chat();">&#10005;</a></div>';
  			element = element + 
  				'<div style="clear: both"></div></div><div class="popup-messages"></div></div>';
  			document.getElementsByTagName("body")[0].innerHTML = 
  				document.getElementsByTagName("body")[0].innerHTML + element; 
  				
  			/* assign new current chat */
  			cur_chat = friend;
  			var elem = document.getElementById(cur_chat);
  			elem.style.display = "block";
  		};
  		
  		function close_chat() {
  			/* close current chat popup */
  			var element = document.getElementById(cur_chat);
  			element.style.display = "none";
  		};
  	</script>
  	
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


 	 %>
  
	<div class="chat-sidebar">
	<%for(String friend : friends) {%>
		<div class="sidebar-name">
			<a href="javascript:open_chat('<%=friend%>')">
				<span><%=friend%></span>
			</a>
		</div>
		<%}%>
	</div>
	<input type='button' value='chat' onclick='send()'/>
	
	
    
	<script>
		var client = new Faye.Client('http://localhost:8123/faye', {timeout: 20});
		<%String uname = "/" + (String) request.getSession().getAttribute("username");%>
		client.subscribe('<%=uname%>', function(message) {
			$('#messages').append('<p>' + message.text + '</p>');
		});
	</script>
	
	<script>
		function send() {
			client.setHeader('dest', 'james');
			client.publish('/james', {text: 'Hello james, from ya boi'});
		}
	</script>
	
	
<body>	
</body>
</html>