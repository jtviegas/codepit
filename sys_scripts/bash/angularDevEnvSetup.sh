#!/bin/sh

sudo npm install -g npm
sudo npm install -g grunt-cli bower
sudo npm install -g yo
yo

bower install angular-bootstrap --save
bower install grunt-karma --save
npm install jit-grunt --save-dev
npm install grunt-karma --save-dev
