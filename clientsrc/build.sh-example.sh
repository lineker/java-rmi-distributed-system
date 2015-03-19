#!/bin/bash
PROJ=/Users/tomazeli/BoxDocuments/School/COMP512/project/clientsrc

rm $PROJ/*.class
javac -Xlint $PROJ/client.java
