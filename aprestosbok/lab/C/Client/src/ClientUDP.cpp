//============================================================================
// Name        : ClientUDP.cpp
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
#include "../lib/ClientUDP.h"
#include "../lib/Exceptions.h"
#include "../lib/Constants.h"

using namespace std;

ClientUDP::ClientUDP()
{
	this->status = OFF;
}

ClientUDP::~ClientUDP()
{
	// TODO Auto-generated destructor stub
}

int ClientUDP::link()
{
	this->log("@link");
	int _result = OK;

	struct sockaddr_in _address;
	_address.sin_family = AF_INET;
	_address.sin_addr.s_addr = INADDR_ANY;
	_address.sin_port = INADDR_ANY;

	if (ON == this->status)
		return _result;

	if((this->socket_fd = socket(AF_INET, SOCK_DGRAM, 0)) == -1)
			return EX_GET_SOCKET_FAILED;

	if(bind(this->socket_fd, (struct sockaddr *)&_address, sizeof(_address)) == -1)
			return EX_BIND_FAILED;

	this->status = ON;
	this->log("link@");
	return _result;
}

int ClientUDP::unlink()
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


void ClientUDP::log(char* _s)
{
	char *_buf = new char[LOG_STRING_LENGTH];

	sprintf(_buf, "[ClientUDP] %s", _s);
	cout << _buf << endl;

	delete _buf;

}
