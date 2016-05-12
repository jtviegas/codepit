#!/bin/bash

LIB=/home/joaovieg/workspace/eclipse/dev/bluemix/xperiment/lib

mvn install:install-file -Dfile=$LIB/wink-1.2.1-incubating.jar -DgroupId=org.apache -DartifactId=wink -Dversion=1.2.1-incubating -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=$LIB/twaclient.jar -DgroupId=com.ibm -DartifactId=twaclient -Dversion=0.0.1 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=$LIB/tdwcsimpleui_public.jar -DgroupId=com.ibm -DartifactId=tdwcsimpleui_public -Dversion=0.0.1 -Dpackaging=jar -DgeneratePom=true

mvn install:install-file -Dfile=$LIB/tdwccronparser.jar -DgroupId=com.ibm -DartifactId=tdwccronparser -Dversion=0.0.1 -Dpackaging=jar -DgeneratePom=true

