PROJ=/home/2009/ltomaz/comp512/

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

cd $PROJ/clientsrc;
#java -Djava.security.policy=$PROJ/clientsrc/java.policy client mimi 3000 1 true
#java -Djava.security.policy=$PROJ/clientsrc/java.policy client mimi 3000
else
cd ..
fi
