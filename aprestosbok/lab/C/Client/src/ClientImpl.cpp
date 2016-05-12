/*
 * ClientImpl.cpp
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
#include "../lib/ClientImpl.h"
#include "../lib/Exceptions.h"
#include "../lib/Constants.h"
using namespace std;

ClientImpl::ClientImpl()
{
	// TODO Auto-generated constructor stub

}

ClientImpl::~ClientImpl()
{
	// TODO Auto-generated destructor stub
}

int ClientImpl::process()
{
	this->log("@process");
	int _result = OK;
	char* _to_send = "going astray...";
	char _message[INPUT_STRING_DEFAULT_LENGTH];

	if(OFF == this->status)
		return EX_NO_CONNECTION;


	if(send(this->socket_fd,_to_send,strlen(_to_send),0)>0)
	{
		this->log("sent message to server:");
		this->log(_to_send);
	}

	if(recv(this->socket_fd, _message,INPUT_STRING_DEFAULT_LENGTH, 0) > 0)
	{
		this->log("received data:");
		this->log(_message);
	}

	this->log("process@");
	return _result;

}
