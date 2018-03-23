#!/bin/sh

cd osc-project-6/Exchange/jars
java -jar server.jar | osascript -e 'tell application "Terminal" to activate' -e 'tell application "System Events" to tell process "Terminal" to keystroke "t" using command down' | java -jar client.jar
