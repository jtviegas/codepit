#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

# include file
#. $this_folder/VARS.sh

echo '{"httpMethod": "POST", "body": "{\"vote\": \"spaces\"}"}' | TABLE_NAME="vote-spaces-tabs" AWS_SAM_LOCAL=true  sam local invoke "VoteSpacesTabs"

