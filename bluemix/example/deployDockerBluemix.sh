#!/bin/sh

CONTAINER_NAME=mynodeappblue
REGISTRY=registry-ice.ng.bluemix.net
ORG=techdays


#stop and remove container
ice stop $CONTAINER_NAME
sleep 8
ice rm $CONTAINER_NAME
sleep 8

#remove image
ice rmi $REGISTRY/$ORG/$CONTAINER_NAME
#ice --local pull $REGISTRY/ibmnode
#rebuild and tag image
#ice --local build -t $CONTAINER_NAME .

ice --local tag --force=true $CONTAINER_NAME:latest $REGISTRY/$ORG/$CONTAINER_NAME:latest
echo "tagging end"
ice --local push $REGISTRY/$ORG/$CONTAINER_NAME:latest
echo "pushing end"
ice -v run --publish 8080 --name $CONTAINER_NAME $REGISTRY/$ORG/$CONTAINER_NAME:latest
echo "run end"
sleep 8
ice logs $CONTAINER_NAME
