#!/bin/sh

# (Note:) This will install Java 8 if its not already installed. 
# If you are currently running a later version of java use the following brew to 
# reinstall the latest version:
# brew cask install caskroom/versions/java

#Checking Version of Java
if type -p java; then
    echo found java executable in PATH
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo found java executable in JAVA_HOME     
    _java="$JAVA_HOME/bin/java"
else
    echo "no java"
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo version "$version"
    if [[ "$version" > "1.8" ]] && [[ "$version" < "1.9" ]]; then
         echo Your Version of Java is more than 1.8 but less than 1.9
	 echo No need to Upgrade... Skipping Installation Step

	 #Running Application
	 echo Running Application... 
	 ./run.sh
    elif [[ "$version" > "1.9" ]]; then        
         echo Your Version of Java is greater than 1.9

	 #Running Application
	 echo Running Application...
	 ./run.sh
    else 
 	 echo Your Version of Java is less than 1.8
	 echo Upgrading to Java 8...

	 #With Homebrew we will install Java 8
	 /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
	 brew update
	 brew tap caskroom/cask
	 brew cask install caskroom/versions/java8

	 #Running Application
	 echo Running Application...
	 ./run.sh
    fi
fi


