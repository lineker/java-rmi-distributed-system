#!/bin/bash

PROJ=/home/2009/ltomaz/comp512/

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar

cd $PROJ/clientsrc;

for (( i = 0; i <= $1 ; i++ ))
do
   java -Djava.security.policy=$PROJ/clientsrc/java.policy client mimi 3000 $i $3 $2 &
done
