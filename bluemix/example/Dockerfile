FROM node:0.10-onbuild

# FROM registry-ice.ng.bluemix.net/ibmnode:latest
# RUN apt-get -y --fix-missing update
# RUN DEBIAN_FRONTEND=noninteractive apt-get -y install git sed bc vim ssh

WORKDIR /
RUN mkdir -p /dist
ADD dist /dist
ADD package.json /
RUN npm install -d --production 
EXPOSE 8080
CMD ["node", "dist/backend/app.js"]
