#!/bin/sh

. ./VARS.sh

if [ -z $SOLR_CLUSTER_ID ] || [ -z $SOLR_USR ] || [ -z $SOLR_PSWD ]
then
	echo "£$%^&* !!! please tell me cluster id , user and password !!! leaving ... "
	return 1
fi

curl -i -X DELETE -u "$SOLR_USR":"$SOLR_PSWD" "$R_AND_R_API/v1/solr_clusters/$SOLR_CLUSTER_ID"