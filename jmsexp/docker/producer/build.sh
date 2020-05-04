#!/bin/sh

this_folder=$(dirname $(readlink -f $0))
docker_folder=$(dirname $this_folder)
base_folder=$(dirname $docker_folder)


# include file
. $this_folder/VARS.sh

PROD_SRC=$base_folder/$PROD
docker_prod=$docker_folder/producer/

_pwd=`pwd`
echo "going to build $NAME app..."

cd $PROD_SRC
mvn clean install
cp target/$JAR $docker_prod
echo "... done."

echo "going to build image $NAME and push it to docker hub..."
cd $docker_prod

docker rmi $NAME:$VERSION
docker build -t $NAME:$VERSION .
docker tag $NAME:$VERSION $DOCKER_HUB_IMG:$VERSION
docker push $DOCKER_HUB_IMG

cd $_pwd
echo "... done."