#!/bin/bash

java -jar swagger-codegen-cli.jar generate -i swagger.json -l jaxrs -DmodelPackage=org.aprestos.labs -DapiPackage=org.aprestos.labs -Djava8=true -DuseBeanValidation=true -DserverPort=9080 -DinvokerPackage=org.aprestos.labs -DserializableModel=true -DdateLibrary=java8 
