/*
 * ClientUDPImpl.h
 *
 *  Created on: Feb 4, 2010
 *      Author: dev
 */

#ifndef CLIENTUDPIMPL_H_
#define CLIENTUDPIMPL_H_

#include "../lib/ClientUDP.h"

class ClientUDPImpl: public ClientUDP
{
public:
	ClientUDPImpl();
	virtual ~ClientUDPImpl();
	int process();
};

#endif /* CLIENTUDPIMPL_H_ */
