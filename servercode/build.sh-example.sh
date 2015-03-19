#!/bin/bash
PROJ=/Users/tomazeli/BoxDocuments/School/COMP512/project/servercode

rm $PROJ/ResImpl/*.class
rm $PROJ/ResInterface/*.class

javac -Xlint $PROJ/ResImpl/ResourceManagerImpl.java
javac -Xlint $PROJ/ResImpl/MiddlewareImpl.java
javac $PROJ/ResInterface/ResourceManager.java
jar cvf ResInterface.jar $PROJ/ResInterface/*.class
