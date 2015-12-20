<link rel="stylesheet" type="text/css" href="/DreamMachine/assets/stylesheets/chat.css">
<link href='https://fonts.googleapis.com/css?family=Raleway' rel='stylesheet' type='text/css'>
<script type="text/javascript" src="http://dreammachine-chat.herokuapp.com/faye/client.js"></script>
<script src="/DreamMachine/assets/javascripts/chat.js"></script>
<script>
  var cf = new ChatFactory("<%= request.getSession().getAttribute("username").toString() %>");
  cf.addChat("choochooman");
  cf.addChat("tingle");
  cf.addChat("chakablocka126");
</script>