/*
 * ClientUDP.h
 *
 *  Created on: Feb 4, 2010
 *      Author: dev
 */

#ifndef CLIENTUDP_H_
#define CLIENTUDP_H_

class ClientUDP {
public:
	ClientUDP();
	virtual ~ClientUDP();
	int link();
	int unlink();
	virtual int process() = 0;
	void log(char* _s);
protected:
	int socket_fd;
	int status;
};

#endif /* CLIENTUDP_H_ */
