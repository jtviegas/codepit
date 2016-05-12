
#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include "../lib/ClientUDPImpl.h"
#include "../lib/UdpClient.h"
#include <signal.h>
#include <string>
using namespace std;

//ClientUDP *o;

UdpClient *o;

void catcher(int sig)
{
	o->unlink();
}




string loadMessage(char* _m, int size)
{

	string _s;

	//_s.append(1,char(0x08));
	//_s.append(1,char(0x80 + 1));	//dest field
	//_s.append(1,char(0x80 + 1));	//dest field
	//_s.append(1,char(32));			//space
	_s.append("1 0 L 1 5 5 0 justa produto lasdka mdj 005432 0 000000 000000 000000 000005 01 00 0 0 1 1 0 00 00 000000 000000 00 0 00000000000000 00 2");

	strcpy(_m,_s.c_str());

}

int boot()
{
	char _to_send[256];
	char _to_receive[256];
	static struct sigaction act;

	memset(_to_receive,256,sizeof(char));
	memset(_to_send,256,sizeof(char));

	loadMessage(_to_send,256);

	act.sa_handler = catcher;
	sigfillset(&(act.sa_mask));
	sigaction(SIGINT, &act, NULL);

	o = new UdpClient("192.168.1.119",2003,6);

	o->link();
	o->send(_to_send, strlen((const char*)_to_send));
	o->receive((char*)_to_receive, 256);
	cout << "received : ";
	cout << _to_receive << endl;
	o->unlink();

	delete(o);

	return EXIT_SUCCESS;
}

int main(void)
{
	return boot();
}
