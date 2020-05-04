#!/bin/sh



cf api https://api.stage1.ng.bluemix.net
cf login -u joaovieg@ie.ibm.com
cf target -o joaovieg@ie.ibm.com -s dev
