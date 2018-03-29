#!/bin/sh

cd osc-project-6/Exchange/jars
echo ‘Starting Server…’
java -jar server.jar | java -jar client.jar
