#!/bin/sh


cd osc-project-6/Exchange/jars
 
java -jar server.jar | osascript -e 'tell application "Terminal" to activate' -e 'tell application "System Events" to tell process "Terminal" to keystroke "t" using command down' -e 'tell application "System Events" to tell process "Terminal" to keystroke "cd osc-project-6/Exchange/jars" ' -e 'tell application "System Events" to tell process "Terminal" to keystroke "\r" ' -e 'tell application "System Events" to tell process "Terminal" to keystroke "java -jar client.jar" ' -e 'tell application "System Events" to tell process "Terminal" to keystroke "\r" '


#-e 'tell application "System Events" to tell process "Terminal" to keystroke "Finder" '

#------------------------------------------------------------
#pwd > destination
#sed 's/^/cd /' destination


#echo destination | sed 's/here/cd /' torun.sh > torun.sh
#sed 's/here/cd '|pwd|'/' osx-run.sh #myrun.sh
#mv destination osc-project-6/Exchange/jars
#cd osc-project-6/Exchange/jars

#./torun.sh

#sed 's/here/'|cat destination|'/' torun.sh
