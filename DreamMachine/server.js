var 	http = require('http'),
     	faye = require('faye'),
     	express = require('express');
     	bayeux = new faye.NodeAdapter({
     		mount: '/faye',
     		timeout: 45
     	});
var 	server = http.createServer();
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
server.listen(8765);
