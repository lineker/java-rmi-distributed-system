#!/bin/bash

PROJ=/home/2009/ltomaz/comp512/

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercodetcp:$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar

echo "starting Middleware"
cd $PROJ/servercodetcp;
java RequestHandlerTester.MiddleTester 3033 Flight:skinner:3031 Car:skinner:3030 Hotel:skinner:3032;
