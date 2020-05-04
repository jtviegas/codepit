#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
parent_folder=$(dirname $this_folder)

# include file
. $this_folder/VARS

$this_folder/buildDockerImage.sh -v $VERSION $BUNDLE_BUILD_SWITCH



