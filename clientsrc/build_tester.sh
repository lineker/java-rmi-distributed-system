#!/bin/bash

PROJ=/home/2009/ltomaz/comp512/
export CLASSPATH=$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar
PROJ=/home/2009/ltomaz/comp512/clientsrc

rm $PROJ/*.class
javac -Xlint $PROJ/RMIClient.java

java -Djava.security.policy=$PROJ/clientsrc/java.policy RMIClient 1 mimi 3000 true
