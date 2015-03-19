#!/bin/bash

PROJ=/home/2009/ltomaz/comp512/

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar

echo "starting Middleware"
cd $PROJ/servercode;
java -Djava.rmi.server.codebase=file:$PROJ/servercode -Djava.security.policy=$PROJ/servercode/java.policy ResImpl.MiddlewareImpl 3038 Hotel:skinner:3035 Flight:skinner:3036 Car:skinner:3037;
