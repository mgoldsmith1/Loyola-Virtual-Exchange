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





### TO DO ###

* Configuration
* Dependencies
* Database configuration
* How to run tests
* Deployment instructions
* Image Placeholder

## Requirements ## 

* JRE Java SE 8 v1.8.0_121

	
## Installation ##

Cloning Market-Exchange-Simulator with Git
```bash
git clone https://github.com/mgoldsmith1/Market-Exchange-Simulator.git
cd Market-Exchange-Simulator
```


On OS X and Windows you can install Maven with Homebrew:

If you don't have homebrew. Install homebrew with the following:
/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"

Install maven:
```bash 
brew install maven
```

Then in the project directory run the following in terminal:
```bash
mvn validate
mvn compile
mvn test
mvn package
mvn verify
mvn install
```

Currently working on a Makefile to establish project dependencies/resources


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
* Link to Contribution.MD

## GPL v3.0 License ##

Link to GNU General Public License
https://www.gnu.org/licenses/gpl-3.0.en.html
