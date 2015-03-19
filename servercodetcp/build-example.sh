#!/bin/bash
PROJ=/Users/tomazeli/BoxDocuments/School/COMP512/project/servercodetcp

rm $PROJ/ResImpl/*.class
rm $PROJ/ResInterface/*.class

javac -Xlint $PROJ/ResImpl/RMHashtable.java 
javac -Xlint $PROJ/ResImpl/MiddlewareImpl.java 
javac -Xlint $PROJ/ResImpl/ResourceManagerImpl.java
javac -Xlint $PROJ/ResImpl/MiddlewareImpl.java $PROJ/ResImpl/ResourceManagerImpl.java $PROJ/ResImpl/RequestHandler.java 
javac -Xlint $PROJ/ResImpl/MiddlewareImpl.java $PROJ/ResImpl/ResourceManagerImpl.java $PROJ/ResImpl/RequestHandler.java $PROJ/RequestHandlerTester/RequestHandlerTester.java
javac -Xlint $PROJ/ResImpl/MiddlewareImpl.java $PROJ/ResImpl/ResourceManagerImpl.java $PROJ/ResImpl/RequestHandler.java $PROJ/ResImpl/RMHashtable.java $PROJ/RequestHandlerTester/MiddleTester.java
javac $PROJ/ResInterface/ResourceManager.java 
jar cvf ResInterface.jar $PROJ/ResInterface/*.class
