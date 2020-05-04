FROM java:openjdk-8-jre

ENV APP_DIR /opt/app

RUN mkdir -p $APP_DIR

ADD target/ibmmqjmsclient-0.0.1-SNAPSHOT.jar $APP_DIR/ibmmqjmsclient-0.0.1-SNAPSHOT.jar
ADD start.sh $APP_DIR/start.sh

WORKDIR $APP_DIR

CMD [ "./start.sh" ]

