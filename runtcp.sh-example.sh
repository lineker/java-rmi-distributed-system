#!/bin/bash

PROJ=/Users/tomazeli/BoxDocuments/School/COMP512/project

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercodetcp:$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar

#build resource manager & middleware
eval $PROJ/servercodetcp/build.sh

#build client
eval $PROJ/clientsrc/build.sh

if [ "$1" != "y" ]; then
echo "Compilation ok? (y/n)"
read RESP
else
RESP=y
fi

if [ $RESP = "y" ]; then

#open resource managers
echo "starting RM Hotel"
xterm -rightbar -bg white -fg black -geometry 80x25 -T "RM Flight" -e "cd $PROJ/servercodetcp;java RequestHandlerTester.RequestHandlerTester 2013; bash" &

echo "starting RM Flight"
xterm -rightbar -bg white -fg black -geometry 80x25+485+0 -T "RM Car" -e "cd $PROJ/servercodetcp;java RequestHandlerTester.RequestHandlerTester 2014; bash" &

echo "starting RM Car"
xterm -rightbar -bg white -fg black -geometry 80x25+970+0 -T "RM Hotel" -e "cd $PROJ/servercodetcp;java RequestHandlerTester.RequestHandlerTester 2015; bash" &

#echo "Hit enter to start middleware after all RMs are done loading"
#read

echo "starting Middleware"
xterm -rightbar -bg white -fg black -geometry 80x25+0+350 -T "Middleware" -e "cd $PROJ/servercodetcp;java RequestHandlerTester.MiddleTester 2012 Flight:localhost:2013 Car:localhost:2014 Hotel:localhost:1015; bash" &

#start client
#echo "Hit enter to start client after all RMs and middleware are done loading"
#read 
echo "starting Client"
xterm -rightbar -bg white -fg black -geometry 80x25+485+350 -T "Client" -e "cd $PROJ/clientsrc;java -Djava.security.policy=$PROJ/clientsrc/java.policy client localhost 2012 tcp; bash" &

else
cd ..
fi
