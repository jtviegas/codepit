FROM ubuntu:14.04
ENV APP_DIR /opt/app
ENV SOURCES_DIR $APP_DIR/sources
RUN echo "deb http://cran.ma.imperial.ac.uk/bin/linux/ubuntu trusty/" >> /etc/apt/sources.list
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && apt-get install -q -y --force-yes --no-install-recommends vim libssl-dev libsasl2-dev openjdk-7-jdk r-cran-rjava r-base r-base-core r-base-dev libopenblas-base apt-utils 
RUN R CMD javareconf
RUN mkdir -p $APP_DIR
ADD app $APP_DIR
WORKDIR $APP_DIR
RUN ./configR.sh
CMD ["/bin/sh", "startup.sh"]
