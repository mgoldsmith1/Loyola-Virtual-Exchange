# Loyola Virtual Exchange #
<img src="https://user-images.githubusercontent.com/35674633/37799728-99235646-2dee-11e8-975b-588a411163fd.png" width="450">

##  Mission Statement ##

Welcome to the Loyola Virtual Exchange.

An open source trading simulator that uses the FIX Protocol that allows users to trade Virtual Currency products on the Loyola Exchange. It will also provide the ability to test market strategies.

## How it works ## 
![screenshot](https://user-images.githubusercontent.com/25426180/37797273-5e4d6b58-2de7-11e8-96e6-d7263c65271e.png)

Loyola Virtual Currency Products:

Virtual Currency Futures:
```bash
Name:			        Symbol	    Contract	Expires	        Price	Change
Loyola Rambler Futures	        LRF-M5Y18   MAY 2018	MAY 2018	0.10	+0.0000000

Name:			        Symbol	    Contract	Expires	        Price	Change
Loyola micro-Rambler Futures	MLR-M5Y18  MAY 2018	MAY 2018	0.01	+0.0000000
```
Contract Specs:
```bash
Minimum Price Fluctuation:	Outrights: .0025 USD per LRF increments ($0.05 USD).

In other words: 
A 1 lot Loyola Rambler Futures Contract = 0.05 cents up or down. 

For example: 

Long(Buy):
If the price goes up .0025 from .10 the quote from LRF will be .1025. At this point you can close at .1025 and make a 0.05 cent profit.
Likewise if the price goes down .0025 from .10 the quote from LRF will be .0975. At this point you can close at .0975 to make a 0.05 cent loss.

Short(Sell):
If the price goes down .0025 from .10 the quote from LRF will be .0975. At this point you can close at .0975 to make a 0.05 cent gain.
Likewise if the price goes up .0025 from .10 the quote from LRF will be .1025. At this point you can close at .1025 and make a 0.05 cent loss.
```

Then we need to do the accounting portion of the project

## Requirements ## 

* JRE Java SE 8 v1.8.0_121

To install Java 8 you can do so with [Homebrew](https://brew.sh/)

To install Homebrew enter the following at the command line:
```bash
$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```
Now you can install Java 8 using Homebrew:
```bash
$ brew update
$ brew tap caskroom/cask
$ brew cask install caskroom/versions/java8
```
Check Version:
```bash
$ java -version
java version "1.8.0_121"
Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)
```

## Installation ##

Cloning Market-Exchange-Simulator with Git
```bash
$ git clone https://github.com/mgoldsmith1/Market-Exchange-Simulator.git
$ cd Market-Exchange-Simulator

```

On OSX, Linux, and Windows from a terminal you can run the application via the following:
```bash
$ ./run.sh
```
To run the client and server individually:

Step 1: Run the server:
```bash 
$ cd osc-project-6/Exchange/jars
$ java -jar server.jar
```
Step 2: In the same directory run the client:
```bash 
$ java -jar client.jar
```
You can also create a script to run the server and client simultaneously using a pipeline:
```bash 
$ cd osc-project-6/Exchange/jars
$ java -jar server.jar | java -jar client.jar
```
From any directory on your local machine, you can also automate the entire sequence of requirements/installation steps by running the install-run.sh file. (Note: If you already have Homebrew and Java 8 the installation will skip this steps, clone, then run the project):
```bash 
$ ./install-run.sh
```

## Features List: ##

* Provides live updates of financial exchange information.
* Integrate the Financial Information Exchange Application Programming Interface to:
* Provides simulation of message/order routing.
* Provide string responses for financial exchange quotes for clients to trade based on.

## Functional vs Non-Functional Requirements ##

* Functional Requirements
	- Provides multi-threaded order traffic engine.
		
* Non-Functional Requirements
	- Provides the ability for the simulation of a financial exchange.
	- Provides delegation of an exchange feed.

## Frequently Asked Questions (FAQ) ##
* What is Market-Exchange-Simulator?	
   - _Market Exchange Simulator is an open source trading simulator that uses the FIX Protocol and will allow users to test market strategies._

* Which software license is Market-Exchange-Simulator licensed under?	
   - _Market-Exchange-Simulator is licensed under the GNU General Public License v3.0. Refer to the license document for more information._

* Which platforms does Market-Exchange-Simulator support?	
   - _Market-Exchange-Simulator is actively tested and supported on various 64 bit versions of Windows (7 and up) and macOS._

* Who made Market-Exchange-Simulator? 	
   - _Group6 from COMP312/412 course. The list of contributors can be found on GitHub._

* Are there indicators provided?	
   - _No. Possibly in the future._

* How do I report a bug?	
   - _On the project's Github page under the issues tab please submit a new issue ticket with a descriptive but brief subject line. In the main body please provide adequate information for contributors to solve the issue._

* Who do I talk to? 
   - _Repo owner or admin_
   - _Other community members or team contact_


## IRC channel ##

* ##MES

## Development ##

* Writing tests
* Code review
* Other guidelines
* [Contribution.md](https://github.com/mgoldsmith1/Market-Exchange-Simulator/blob/master/osc-project-6/Contributions.md)

## GPL v3.0 License ##

[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)

