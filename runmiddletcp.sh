#!/bin/bash

PROJ=/home/2009/ltomaz/comp512/

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercodetcp:$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar

echo "starting Middleware"
cd $PROJ/servercodetcp;
java RequestHandlerTester.MiddleTester 2012 Flight:skinner:2013 Car:skinner:2014 Hotel:skinner:1015;
