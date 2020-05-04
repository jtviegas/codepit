var http = require('http');

var PORT=8080;
if(process.env.PORT)
	PORT=process.env.PORT;

var server = http.createServer(
	function(request, response){
    		response.end('Hello Cork!');
	}
);

server.listen(PORT, function () {
  console.log('Example app listening at http://%s:%s',
	 server.address().address, 
	server.address().port);
});
