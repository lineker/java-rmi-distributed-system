#!/bin/bash

#rmiregistry 3000 & --> middleware registry port
#rmiregistry 3001 & --> RM registry port

PROJ=/Users/tomazeli/BoxDocuments/School/COMP512/project

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar
#build resource manager & middleware
eval $PROJ/servercode/build.sh

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
xterm -rightbar -bg white -fg black -geometry 80x25 -T "RM Hotel" -e "cd $PROJ/servercode;java -Djava.rmi.server.codebase=file:$PROJ/project/servercode -Djava.security.policy=$PROJ/servercode/java.policy ResImpl.ResourceManagerImpl 2013 Hotel localhost; bash" &

echo "starting RM Flight"
xterm -rightbar -bg white -fg black -geometry 80x25+485+0 -T "RM Flight" -e "cd $PROJ/servercode;java -Djava.rmi.server.codebase=file:$PROJ/servercode -Djava.security.policy=$PROJ/servercode/java.policy ResImpl.ResourceManagerImpl 2014 Flight localhost; bash" &

echo "starting RM Car"
xterm -rightbar -bg white -fg black -geometry 80x25+970+0 -T "RM Car" -e "cd $PROJ/servercode;java -Djava.rmi.server.codebase=file:$PROJ/servercode -Djava.security.policy=$PROJ/servercode/java.policy ResImpl.ResourceManagerImpl 2015 Car localhost; bash" &

echo "Hit enter to start middleware after all RMs are done loading"
read

echo "starting Middleware"
xterm -rightbar -bg white -fg black -geometry 80x25+0+350 -T "Middleware" -e "cd $PROJ/servercode;java -Djava.rmi.server.codebase=file:$PROJ/servercode -Djava.security.policy=$PROJ/servercode/java.policy ResImpl.MiddlewareImpl 2012 Hotel:localhost:3001 Flight:localhost:3001 Car:localhost:3001; bash" &

#start client
echo "Hit enter to start client after all RMs and middleware are done loading"
read 
echo "starting Client"
xterm -rightbar -bg white -fg black -geometry 80x25+485+350 -T "Client" -e "cd $PROJ/clientsrc;java -Djava.security.policy=$PROJ/clientsrc/java.policy client localhost 3000; bash" &

else
cd ..
fi
