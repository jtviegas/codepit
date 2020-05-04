FROM ubuntu:14.04

RUN echo "deb http://ftp.heanet.ie/mirrors/cran.r-project.org/bin/linux/ubuntu trusty/" >> /etc/apt/sources.list
ENV DEBIAN_FRONTEND noninteractive
RUN apt-get update && apt-get install -q -y --force-yes --no-install-recommends vim libssl-dev libsasl2-dev openjdk-7-jdk r-cran-rjava r-base r-base-core r-base-dev libopenblas-base apt-utils 
RUN R CMD javareconf

WORKDIR /
ADD configR.sh /configR.sh
RUN ./configR.sh

RUN mkdir -p /opt/app/r
ADD startup.sh /opt/app/startup.sh
ADD topicConsumer.R /opt/app/topicConsumer.R
ADD r /opt/app/r

WORKDIR /opt/app
CMD ["/bin/sh", "startup.sh"]
