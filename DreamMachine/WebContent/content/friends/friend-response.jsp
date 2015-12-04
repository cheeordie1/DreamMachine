<div>
	<%String sender = (String) request.getAttribute("sender"); %>
	<label><%=sender%></label><br>
	<form action="/DreamMachine/response" method="post">
		accept <input type="radio" name="name" value="accept"><br>
		reject <input type="radio" name="name" value="reject"><br>
		block <input type="radio" name="name" value="block"><br>
		
		<input type="submit" value="submit">
		<input type="hidden" name="sender" value="<%=sender%>">
	</form>
</div>