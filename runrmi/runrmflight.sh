#!/bin/bash

PROJ=/home/2009/ltomaz/comp512/

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar

echo "starting RM Flight"
cd $PROJ/servercode;
java -Djava.rmi.server.codebase=file:$PROJ/servercode -Djava.security.policy=$PROJ/servercode/java.policy ResImpl.ResourceManagerImpl 3036 Flight mimi;
