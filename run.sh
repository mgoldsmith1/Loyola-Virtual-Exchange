#!/bin/sh

cd osc-project-6/Exchange/jars
echo ‘Starting Client…’
java -jar client.jar

#echo ‘Starting Server…’
#need a ordermatch jar with a pipeline to client jar
#java -jar server.jar | java -jar client.jar
