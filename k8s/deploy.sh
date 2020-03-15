#!/bin/sh

this_folder="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
parent_folder=$(dirname $this_folder)

echo " --- kubernetes local deployment --- "

WEBSERVER_DEPLOYMENT_YAML=$this_folder/webserver.yaml
WEBSERVER_SVC_DEPLOYMENT_YAML=$this_folder/webserver-svc.yaml

# parameter check
usage()
{
        cat <<EOM
        usage:
        $(basename $0) doit | undoit
EOM
        exit 1
}

[ -z $1 ] && { usage; }

doit()
{
    echo "installing ..."
    local __r=0
    
    kubectl create -f $WEBSERVER_DEPLOYMENT_YAML
    if [ ! "$?" -eq "0" ]
    then
        echo "...couldn't do the deployment"
        __r=1
        return $__r
    fi
    
    kubectl create -f $WEBSERVER_SVC_DEPLOYMENT_YAML
    if [ ! "$?" -eq "0" ]
    then
        echo "...couldn't do the deployment"
        __r=1
        return $__r
    fi
    
    echo "...deployed successfully"
    return $__r
}

undoit()
{
    echo "undoing deployment..."
    local __r=0
    
    kubectl delete deployments webserver
    if [ ! "$?" -eq "0" ]
    then
        echo "...could not undo deployment"
        __r=1
    else
        echo "...removed deployment"
    fi
    
    kubectl delete svc web-service
    if [ ! "$?" -eq "0" ]
    then
        echo "...could not delete service"
        __r=1
    else
        echo "...removed service"
    fi
    
    return $__r
}

if [ "$1" == "doit" ]
then
	doit
    if [ ! "$?" -eq "0" ]
    then
        echo "...deployment was unsuccessful"
    else
        echo "...deployment was successful"
    fi
elif [ "$1" == "undoit" ]
then
	undoit
    if [ ! "$?" -eq "0" ]
    then
        echo "...undeployment was unsuccessful"
    else
        echo "...undeployment was successful"
    fi
else
	usage
fi



