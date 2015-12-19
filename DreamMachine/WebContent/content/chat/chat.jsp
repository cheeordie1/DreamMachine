<script type="text/javascript" src="http://dreammachine-chat.herokuapp.com/faye/client.js"></script>
<script src="/DreamMachine/assets/javascripts/chat.js"></script>
<div id="chat-box-container">
  <div id="message-display">
    <ul id="messages"></ul>
  </div>
  <div id="message-submit-container">
    <input id="message-type" />
    <button id="message-send">Send</button>
  </div>
</div>
<script>new Chat("messages", "message-type", "message-send")</script>