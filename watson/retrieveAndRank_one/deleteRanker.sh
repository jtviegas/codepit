#!/bin/sh

. ./VARS.sh

if [ -z $RANKER_ID ] || [ -z $SOLR_USR ] || [ -z $SOLR_PSWD ]
then
	echo "£$%^&* !!! please tell me ranker id , user and password !!! leaving ... "
	return 1
fi

curl -X DELETE -u "$SOLR_USR":"$SOLR_PSWD" "$R_AND_R_API/v1/rankers/$RANKER_ID"
