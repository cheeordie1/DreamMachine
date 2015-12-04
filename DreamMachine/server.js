var 	http = require('http'),
     	faye = require('faye'),
     	express = require('express');
     	bayeux = new faye.NodeAdapter({
     		mount: '/faye',
     		timeout: 45
     	});

	

var app = express();

app.post('/message', function(req, res) {
  console.log('received a message');
  var dest = res.getHeader('dest');
  bayeux.getClient().publish(dest, {text: req.body.message});
  res.writeHead(200, {'Content-Type':'text/plain'});
  response.end("hello bitch");
});

//var 	server = http.createServer(app);
var 	server = http.createServer();
/*var server = http.createServer(function(request, response) {
	response.writeHead(200, {'Content-Type': 'text/plain'});
 	response.end('Hello, non-Bayeux request');
});*/

// ajax publish dest exists in req headers
// example: 'username-dest' : 'jayeve'
//server.post('/chat', function(req, res) {
//	var dest = req.headers['username-dest'];
//	bayeux.getClient.publish(dest, { text: req.body.message});
//});

//var server = http.createServer(function(request, response) {
  //response.writeHead(200, {'Content-Type': 'text/plain'});
  //response.end('Hello, non-Bayeux request');
//});
bayeux.on('handshake', function() {
  console.log("New connection");
});
bayeux.attach(server);

// extension
var traffic = {
	incoming: function (message, request, callback) {
        console.log(message.channel);
		if(message.channel == '/j/requests' || message.channel == '/l/requests') {
			console.log(message.headers);
			console.log(message.channel);
			console.log(request.url);
		}
		return callback(message);
	}

};
bayeux.addExtension(traffic);

server.listen(8123);
