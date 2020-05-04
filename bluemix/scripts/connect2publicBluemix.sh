#!/bin/sh
cf api https://api.ng.bluemix.net
cf login -u joaovieg@ie.ibm.com
#cf target -o joaovieg@ie.ibm.com -s dev
cf target -o apaa -s dev
