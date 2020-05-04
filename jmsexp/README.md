# jmsexp
jms/activemq spring experiment

check inside `docker` folder, there are the container specific folders and inside the scripts to build and load them:
- activemq broker;
- consumer - java jms client to consume messages from queue "testQueue";
- producer - java jms client to produce messages to queue "testQueue";

...one can also use docker-compose to start all three containers, check the `run.sh` script inside `docker` folder.
