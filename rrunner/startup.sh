#!/bin/sh

_pwd=`pwd`
export R_SCRIPTS_DIR=$_pwd/r
Rscript topicConsumer.R  twitter
