<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<jsp:include page="../header/top-bar.jsp" />
<script type="text/javascript" src="http://localhost:8123/faye/client.js"></script>
<script>
	<%String sender = (String) request.getSession().getAttribute("username"); %>
	
	function send_request(destination) {
		var client = new Faye.Client('http://localhost:8123/faye', {timeout: 20});
		client.publish("/" + destination + "/requests", "<%=sender%>");
		client.disconnect(); // TODO maybe have to wait?
				
		$.ajax({
			type: 'POST',
			url: '/DreamMachine/friendRequest',
			data: { message : destination },
			success: function(result) {
				window.location.href = '/DreamMachine/home';
			},
			error: function() {
				window.location.href = '/DreamMachine/home';
			}
		});
		
		
	};
</script>
<div>
	<%String destination = (String) request.getAttribute("sender"); %>
	<a href="javascript:send_request('<%=destination%>')">send a request to <%=destination%></a>
</div>
</html>