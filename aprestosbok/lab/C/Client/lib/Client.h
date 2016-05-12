/*
 * Client.h
 *
 *  Created on: Feb 4, 2010
 *      Author: dev
 */

#ifndef CLIENT_H_
#define CLIENT_H_

class Client {
public:
	Client();
	virtual ~Client();
	int link();
	int unlink();
	virtual int process() = 0;
	void log(char* _s);
protected:
	int socket_fd;
	int status;
};

#endif /* CLIENT_H_ */
