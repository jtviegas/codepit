#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

java -jar $this_folder/indexer/target/indexer-0.0.1-SNAPSHOT.jar -input $this_folder/indexer/enron.zip

__r=$?
if [ ! "$__r" -eq "0" ] ; then echo "could not index docs !...leaving." && return 1; fi

echo "overall outcome: $__r"
