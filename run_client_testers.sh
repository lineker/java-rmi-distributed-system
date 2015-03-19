PROJ=/home/2009/ltomaz/comp512/

echo "Setting CLASSPATH"
export CLASSPATH=$PROJ/servercode:$PROJ/clientsrc:$PROJ/ResInterface.jar


cd $PROJ/clientsrc;
java -Djava.security.policy=$PROJ/clientsrc/java.policy client mimi 3000 1 true
#java -Djava.security.policy=$PROJ/clientsrc/java.policy client mimi 3000
