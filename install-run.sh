#!/bin/sh

# (Note:) This will install Java 8 if its not already installed. 
# If you are currently running a later version of java use the following brew to 
# reinstall the latest version:
# brew cask install caskroom/versions/java

/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

brew update
brew tap caskroom/cask
brew cask install caskroom/versions/java8

java -version

git clone https://github.com/mgoldsmith1/Market-Exchange-Simulator.git
cd Market-Exchange-Simulator

./run.sh

#cd osc-project-6/Exchange/jars

#java -jar server.jar
