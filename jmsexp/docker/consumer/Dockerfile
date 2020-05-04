FROM java:openjdk-8-jre

ENV TMP_DIR /tmp
ENV APP_DIR /opt/app

RUN apt-get update && apt-get install -q -y --no-install-recommends wget

RUN mkdir -p $APP_DIR
ADD start.sh $APP_DIR/start.sh
ADD jmsqueuecons-0.0.1-SNAPSHOT.jar $APP_DIR/jmsqueuecons-0.0.1-SNAPSHOT.jar

WORKDIR $APP_DIR
CMD [ "./start.sh" ]