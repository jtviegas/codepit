#!/bin/bash

# creating a java lib project scaffold
gradle init --type java-library

gradle tasks

gradle hi -Pdohi

# set logger level
 gradle --info | --debug  build
 
# generates report about build in build/reports/profile
# build, dependency, running times  
 gradle --profile clean build

# try to build sub-projects, independent projects in parallel 
gradle --parallel clean build

# exclude a particular task
gradle -x taskx build

# convert maven into gradle project
gradle init --type pom



