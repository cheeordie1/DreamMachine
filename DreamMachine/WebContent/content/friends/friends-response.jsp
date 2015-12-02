<div>
	<%String sender = (String) request.getAttribute("sender"); %>
	<label><%=sender%></label><br>
	<form action="friendResponse" method="post">
		<input type="radio" name="accept">
		<label> accept</label><br>
		<input type="radio" name="reject">
		<label> reject</label><br>
		<input type="radio" name="block">
		<label> block</label><br>
		<input type="submit" value="submit">
		<input type="hidden" name="sender" value="<%=sender%>">
	</form>
</div>