#!/bin/sh



echo "kubernetes local config script"

# parameter check
usage()
{
        cat <<EOM
        usage:
        $(basename $0) install | uninstall
EOM
        exit 1
}

[ -z $1 ] && { usage; }

install()
{
    echo "installing ..."
    local __r=0
    
    curl -Lo minikube $MINIKUBE_URL && chmod +x minikube && sudo mv minikube /usr/local/bin/
    if [ ! "$?" -eq "0" ]
    then
        echo "...could not install minikube"
        __r=1
        return $__r
    else
        echo "...installed minikube"
    fi
    
    curl -LO https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/darwin/amd64/kubectl && chmod +x ./kubectl && sudo mv ./kubectl /usr/local/bin/kubectl
    if [ ! "$?" -eq "0" ]
    then
        echo "...could not install kubectl"
        __r=1
        return $__r
    else
        echo "...installed kubectl"
        kubectl config view
        kubectl cluster-info
    fi
    
    minikube addons enable heapster
    
    return $__r
}

uninstall()
{
    echo "uninstalling ..."
    local __r=0
    
    minikube addons disable heapster
    
    sudo rm -f /usr/local/bin/minikube
    if [ ! "$?" -eq "0" ]
    then
        echo "...could not uninstall minikube"
        __r=1
    else
        echo "...uninstalled minikube"
    fi
    
    sudo rm -f /usr/local/bin/kubectl
    if [ ! "$?" -eq "0" ]
    then
        echo "...could not uninstall kubectl"
        __r=1
    else
        echo "...uninstalled kubectl"
    fi
    
    minikube stop
    minikube delete
    
    return $__r
}

if [ "$1" == "install" ]
then
	install
    if [ ! "$?" -eq "0" ]
    then
        echo "...install was unsuccessful"
    else
        echo "...install was successful"
    fi
elif [ "$1" == "uninstall" ]
then
	uninstall
    if [ ! "$?" -eq "0" ]
    then
        echo "...uninstall was unsuccessful"
    else
        echo "...uninstall was successful"
    fi
else
	usage
fi



