<script type="text/javascript" src="http://dreammachine-chat.herokuapp.com/faye/client.js"></script>
<script src="/DreamMachine/assets/javascripts/chat.js"></script>
<script src="/DreamMachine/assets/javascripts/form_button.js"></script>
<script>
  new ChatFactory("<%= request.getSession().getAttribute("username").toString() %>");
</script>