#!/bin/sh

cd osc-project-6/Exchange/jars
echo ‘Starting Server…’
java -jar client.jar
# need a ordermatch jar with a pipeline to client jar
#java -jar server.jar | java -jar client.jar
