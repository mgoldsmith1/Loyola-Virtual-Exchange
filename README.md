# Market Exchange Simulator #
<img src="https://user-images.githubusercontent.com/35674633/37799728-99235646-2dee-11e8-975b-588a411163fd.png" width="450">

##  Mission Statement ##

An open source trading simulator that uses the FIX Protocol and will allow users to test market strategies.

## How it works ## 
![screenshot](https://user-images.githubusercontent.com/25426180/37797273-5e4d6b58-2de7-11e8-96e6-d7263c65271e.png)

In our main, we are feeding numbers ranging from 1 to 200 million through an exchange system. This system consists of two threads. The first thread finds what market these numbers should go to. The second thread determines if it is a prime market. In other words if we have found a prime number within our system it will be queued to the Loyola Prime Market Index or (LPX). The numbers are treated like a key value pair. The key representing a ticket number and the value is the information of the order being placed. The numbers are read in like a queue that is FIFO. The processed ordered are feed to a dequeue that are eventually redirected to a market. This is a baseline approach to find a particular difference in what is being feed through the Exchange Feed

We will eventually remove the numbers that are feed here and replace them with strings
Those strings will contain an OrderID, OrderType, OrderSize, PriceBought, PriceSold, etc.
The value we will focus on first is the OrderID's e.g. LPX (Loyola Prime Index), or AAPL, MSFT, etc. This will determine which market the string info will go to. In that Market the string will be parsed into separate variables. From there we will determine if the OrderType is a Buy/Sell, QrderSize (quantity bought/sold), PriceBought $50.25, PriceSold $52.00, etc.

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
You can also automate this entire sequence of installation steps via the install-run.sh file.
Without cloning this repository take the raw contents of install-run.sh and run it from any directory on your local machine:
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

