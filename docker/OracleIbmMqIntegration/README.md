# docker images for studying Oracle Advanced Queeing (AQ) integration with IBM MQ


## requirements

#### all OS's

* download oracle DB (12.1.0.2.0) Enterprise Edition, Linux x86-64 version, from [Oracle site](https://www.oracle.com/technetwork/database/enterprise-edition/downloads/index.html) and place both files (``linuxamd64_12102_database_1of2.zip`` and ``linuxamd64_12102_database_2of2.zip``) in ``oracle/12.1.0.2/``

#### windows

* use docker toolbox
* invoke ``dos2unix oracle/12.1.0.2/*``
* if no available space in the container, change the default docker virtual machine (the docker toolbox one):
    $ docker-machine rm default
    $ docker-machine create -d virtualbox --virtualbox-disk-size "45000" default
* expand virtual machine memory
    * ``docker-machine stop``
    * ``VBoxManage modifyvm default --memory  4096``
    * ``docker-machine start``
* allow 1521 and 5500 db ports forwarding in the docker toolbox virtual machine;
* allow 1414 and 9443 mq ports forwarding in the docker toolbox virtual machine;

## run the experiment

* build and load the containers:
  * invoke ``./build.sh``
  * invoke ``./run.sh``
* use a client to connect to the db once the db has started:
  * username: sys as sysdba
  * password: passw0rd
  * hostname: 127.0.0.1
  * port: 1521
  * SID: oracledb
* connect to the MQ using the web console in ``https://localhost:9443/ibmmq/console`` with ``admin/passw0rd``
* run setup in ``setup.sql`` (we can always revert what is done with ``reset.sql``)
* send messages using ``send_messages.sql``
* check the messages comming from the _ibmmqjmsclient_ container log


## containers setup - doing it separately

#### oracle db - in ``oracle/`` - check [oracle repository](https://github.com/oracle/docker-images/tree/master/OracleDatabase/SingleInstance)

* define variables in ``VARS`` accordingly;
* invoke ``./build.sh`` to build the database image - wait until the script has really finished (!!!);
* invoke ``./run.sh`` to run the database container - notice ``that run.sh`` is adding a custom init script to the launch procedure:
   ** 12.1.0.2/custom00start.sh - runs catmgw.sql to load the oracle agent gateway related packages (mgw_*);
* check docker logs: ``docker logs -f oracledb``
* use a client to connect to the db once the db has started:
  * username: sys as sysdba
  * password: passw0rd
  * hostname: 127.0.0.1
  * port: 1521
  * SID: oracledb

#### IBM MQ - in ``ibmmq/`` - based on [default developer configuration](https://github.com/ibm-messaging/mq-container/blob/master/docs/developer-config.md) container from the [IBM MQ container](https://github.com/ibm-messaging/mq-container) repository on github

* invoke ``./run.sh`` to run the ibmmq container
* connect to the MQ using the web console in ``https://localhost:9443/ibmmq/console`` with ``admin/passw0rd``;

#### IBM MQ jms client - in ``ibmmqjmsclient/`` - simple spring boot jms client that reads the messages from IBM MQ


