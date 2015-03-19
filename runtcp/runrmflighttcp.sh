#!/bin/bash

PROJ=/home/2009/ltomaz/comp512/

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercodetcp:$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar

cd $PROJ/servercodetcp;
java RequestHandlerTester.RequestHandlerTester 3031 mimi 2012;
