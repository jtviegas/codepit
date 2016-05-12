/*
 * ClientUDP.h
 *
 *  Created on: Feb 4, 2010
 *      Author: dev
 */

#ifndef UDPCLIENT_H_
#define UDPCLIENT_H_

#include <string>

class UdpClient
{
public:
	UdpClient();
	UdpClient(char *_ip, int _port);
	UdpClient(char *_ip, int _port, int _timeoutsecs);
	virtual ~UdpClient();
	void setIp(char *_ip);
	void setPort(int _port);
	void setTimeout(int _timeoutsecs);
	int link();
	int unlink();
	int receive(char* _data, int _length);
	int send(char* _data, int _length);
	int getStatus();
protected:
	void log(std::string _s);
	int socket_fd;
	int status;
	int port;
	char *ip;
	int timeoutsecs;
};

#endif /* UDPCLIENT_H_ */
