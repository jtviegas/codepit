//============================================================================
// Name        : Client.cpp
// Author      :
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C, Ansi-style
//============================================================================

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <iostream>
#include "../lib/Client.h"
#include "../lib/Exceptions.h"
#include "../lib/Constants.h"

using namespace std;

Client::Client()
{
	this->status = OFF;
}

Client::~Client()
{
	// TODO Auto-generated destructor stub
}

int Client::link()
{
	this->log("@link");
	int _result = OK;

	if (ON == this->status)
		return _result;

	struct sockaddr_in _address;

	if((this->socket_fd = socket(AF_INET, SOCK_STREAM, 0)) == -1)
		return EX_GET_SOCKET_FAILED;

	_address.sin_family = AF_INET;
	_address.sin_addr.s_addr = inet_addr(SERVER_IP);
	_address.sin_port = SERVER_PORT;

	if(connect(this->socket_fd, (struct sockaddr *)&_address, sizeof(_address)) == -1)
		return EX_CONNECT_FAILED;

	this->status = ON;
	this->log("link@");
	return _result;
}

int Client::unlink()
{
	this->log("@unlink");
	int _result = OK;

	if(this->status != OFF)
	{
		_result = close(this->socket_fd);
		this->status = OFF;
	}

	this->log("unlink@");
	return _result;
}


void Client::log(char* _s)
{
	char *_buf = new char[LOG_STRING_LENGTH];

	sprintf(_buf, "[Client] %s", _s);
	cout << _buf << endl;

	delete _buf;

}
