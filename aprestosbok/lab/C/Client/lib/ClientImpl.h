/*
 * ClientImpl.h
 *
 *  Created on: Feb 4, 2010
 *      Author: dev
 */

#ifndef CLIENTIMPL_H_
#define CLIENTIMPL_H_

#include "../lib/Client.h"

class ClientImpl: public Client
{
public:
	ClientImpl();
	virtual ~ClientImpl();
	int process();
};

#endif /* CLIENTIMPL_H_ */
