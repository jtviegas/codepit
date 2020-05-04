FROM java:openjdk-8-jre

ENV TMP_DIR /tmp
ENV APP_DIR /opt/app
ENV BUNDLE https://archive.apache.org/dist/activemq/5.15.4/apache-activemq-5.15.4-bin.tar.gz

RUN apt-get update && apt-get install -q -y --no-install-recommends wget

RUN mkdir -p $APP_DIR
RUN wget -qO- $BUNDLE | tar zxvf - -C $APP_DIR --strip 1
ADD start.sh $APP_DIR/start.sh

WORKDIR $APP_DIR
CMD [ "./start.sh" ]