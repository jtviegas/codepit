//============================================================================
// Name        : UdpClient.cpp
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
#include <string>
#include <errno.h>
#include "../lib/UdpClient.h"
#include "../lib/Exceptions.h"
#include "../lib/Constants.h"

using namespace std;




UdpClient::UdpClient()
{
	this->status = OFF;
	this->timeoutsecs = TIMEOUT_SECS_DEFAULT;
}

UdpClient::~UdpClient()
{
	// TODO Auto-generated destructor stub
}

UdpClient::UdpClient(char *_ip, int _port)
{
	this->status = OFF;
	this->timeoutsecs = TIMEOUT_SECS_DEFAULT;
	this->ip = _ip;
	this->port = _port;
}

UdpClient::UdpClient(char *_ip, int _port, int _timeoutsecs)
{
	this->status = OFF;
	this->ip = _ip;
	this->port = _port;
	this->timeoutsecs = _timeoutsecs;
}

void UdpClient::setIp(char *_ip)
{
	this->log("@setIp");
	this->ip = _ip;
	this->log("setIp@");
}

void UdpClient::setPort(int _port)
{
	this->log("@setPort");
	this->port = _port;
	this->log("setPort@");
}

void UdpClient::setTimeout(int _timeoutsecs)
{
	this->log("@setTimeout");
	this->timeoutsecs = _timeoutsecs;
	this->log("setTimeout@");
}

int UdpClient::getStatus()
{
	this->log("@getStatus");
	this->log("getStatus@");
	return this->status;
}

int UdpClient::link()
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

int UdpClient::unlink()
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


void UdpClient::log(std::string _s)
{
	char _buf[LOG_STRING_LENGTH];
	memset(_buf,0,LOG_STRING_LENGTH);

	sprintf(_buf, "[UdpClient] %s", _s.c_str());

	cout << _buf << endl;
}

int UdpClient::send(char* _data, int _length)
{
	this->log("@send");

	int _result = OK;
	int _sent_len = 0;
	struct sockaddr_in _server_address;
	string _message(_data);


	_server_address.sin_family = AF_INET;
	_server_address.sin_addr.s_addr = inet_addr(this->ip);
	_server_address.sin_port = this->port;

	if(OFF == this->status)
		return EX_NO_CONNECTION;


	this->log("going to send message to server:");
	this->log(_message.c_str());

	if( (_sent_len = sendto(this->socket_fd,_message.c_str(),_message.length(), 0,
				(struct sockaddr *)&_server_address,
				(socklen_t)sizeof(struct sockaddr_in))) != -1 )
	{
		this->log("sent message server:");
		this->log(_message.substr(0,_sent_len));
	}
	else
	{
		this->log("did not send message to server!");
		return EX_SEND_FAILED;
	}

	this->log("send@");
	return _result;
}

int UdpClient::receive(char* _data, int _length)
{
	this->log("@receive");

	int _result = 0;

	fd_set _fdset;
	struct timeval _timeout;
	int _select_call;
	char* _message;

	FD_ZERO(&_fdset);
	FD_SET(this->socket_fd, &_fdset);

	_timeout.tv_sec = this->timeoutsecs;
	_timeout.tv_usec = 0;

	_select_call = select(this->socket_fd + 1, &_fdset,NULL, NULL, &_timeout);

	if(-1 == _select_call)
	{
		perror("select @receive");
		this->log("exception in select @receive");
		return errno;
	}
	else if( 0 == _select_call)
	{
		this->log("timeout exceeded in select @receive");
		return EX_TIMEOUT;
	}

	_message = new char[_length];

	if( -1 == recv(this->socket_fd, _message,_length, 0) )
	{
		this->log("exception in recv @receive");
		return EX_RECEIVE_FAILED;
	}
	else
	{
		this->log("received message from server:");
		strcpy( _data,_message);
		this->log(string(_data));
	}

	delete _message;

	this->log("receive@");
	return _result;
}
