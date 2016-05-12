/*
 * ClientUDPImpl.cpp
 *
 *  Created on: Feb 4, 2010
 *      Author: dev
 */

#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdlib.h>
#include <cstring>
#include <string>
#include <c++/4.1.1/bits/stringfwd.h>
#include <iostream>
#include "../lib/ClientUDPImpl.h"
#include "../lib/Exceptions.h"
#include "../lib/Constants.h"
using namespace std;

ClientUDPImpl::ClientUDPImpl()
{
	// TODO Auto-generated constructor stub

}

ClientUDPImpl::~ClientUDPImpl()
{
	// TODO Auto-generated destructor stub
}

int ClientUDPImpl::process()
{
	this->log("@process");
	int _result = OK;
	char* _to_send = "going astray...";
	char _message[INPUT_STRING_DEFAULT_LENGTH];

	struct sockaddr_in _server_address;
	_server_address.sin_family = AF_INET;
	_server_address.sin_addr.s_addr = inet_addr(SERVER_IP);
	_server_address.sin_port = SERVER_PORT;

	socklen_t _addr_len = (socklen_t)sizeof(struct sockaddr_in);

	if(OFF == this->status)
		return EX_NO_CONNECTION;

	if( sendto(this->socket_fd,_to_send,strlen(_to_send), 0,
				(struct sockaddr *)&_server_address, _addr_len) > 0 )
	{
		this->log("sent message to server:");
		this->log(_to_send);
	}

	if(recv(this->socket_fd, _message,INPUT_STRING_DEFAULT_LENGTH, 0) > 0)
	{
		this->log("received data from server:");
		this->log(_message);
	}

	this->log("process@");
	return _result;

}
