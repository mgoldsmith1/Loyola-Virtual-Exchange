![screenshot](https://github.com/mgoldsmith1/Loyola-Virtual-Exchange/blob/master/osc-project-6/logo.png)
## Loyola Virtual Exchange ##

![screenshot](https://github.com/mgoldsmith1/Loyola-Virtual-Exchange/blob/master/osc-project-6/floor.jpg)
##  Mission Statement ##

Welcome to the Loyola Virtual Exchange!
 
An open source financial exchange that uses the FIX Protocol to enable university students to create a market between universities and trade Virtual Currency products on an Exchange.  

For more information:
## How it works ## 
![screenshot](https://github.com/mgoldsmith1/Loyola-Virtual-Exchange/blob/master/osc-project-6/ApplicationWorkspace.png)

Loyola Virtual Currency Products:

Virtual Currency Futures:
```bash
Name:			        Symbol	    Contract	Expires	        Price	Change
Loyola Rambler Futures	        LRF         MAY 2018	DEC 2018	0.10	+0.0000000

Name:			        Symbol	    Contract	Expires	        Price	Change
Loyola micro-Rambler Futures	MRF         MAY 2018	DEC 2018	0.01	+0.0000000
```
(LRF) Contract Specs:
```bash
Minimum Price Fluctuation:	Outrights: .0025 USD per LRF increments ($0.05 USD).

In other words: 
A 1 lot (or 1 quantity) Loyola Rambler Futures Contract = 0.05 cents up or down. 

For example: 

Long(Buy):
If the price goes up .0025 from .10 the quote from LRF will be .1025. 
At this point you can close at .1025 and make a 0.05 cent profit.
Likewise if the price goes down .0025 from .10 the quote from LRF will be .0975. 
At this point you can close at .0975 to make a 0.05 cent loss.

Short(Sell):
If the price goes down .0025 from .10 the quote from LRF will be .0975. 
At this point you can close at .0975 to make a 0.05 cent gain.
Likewise if the price goes up .0025 from .10 the quote from LRF will be .1025. 
At this point you can close at .1025 and make a 0.05 cent loss.
```

(MRF) Contract Specs:
```bash
Minimum Price Fluctuation:	Outrights: .00025 USD per MRF increments ($0.01 USD).

In other words: 
A 1 lot (or 1 quantity) Loyola micro-Rambler Futures Contract = 0.01 cent up or down. 

For example: 

Long(Buy):
If the price goes up .00025 from .01 the quote from LRF will be .01025. 
At this point you can close at .01025 and make a 0.01 cent profit.
Likewise if the price goes down .00025 from .01 the quote from LRF will be .00975. 
At this point you can close at .00975 to make a 0.01 cent loss.

Short(Sell):
If the price goes down .0025 from .01 the quote from LRF will be .00975. 
At this point you can close at .00975 to make a 0.01 cent gain.
Likewise if the price goes up .0025 from .01 the quote from LRF will be .01025. 
At this point you can close at .01025 and make a 0.01 cent loss.
```

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

Cloning Loyola-Virtual-Exchange with Git
```bash
$ git clone https://github.com/mgoldsmith1/Loyola-Virtual-Exchange.git
$ cd Loyola-Virtual-Exchange
```

On OSX, Linux, and Windows from a terminal you can run the application via the following:
```bash
$ ./run.sh
```

You can also automate the requirements/installation steps by running the install-run.sh file within your project workspace.
The installation script will check your version of Java and install Java 8 if necessary.
```bash 
$ ./install-run.sh
```

## Features List: ##

* Provides live updates of financial exchange information.
* Integrate the Financial Information Exchange Application Programming Interface to:
    - Provide simulation of message/order routing.
    - Provide string responses containing financial exchange quotes for clients to trade based on.

## Functional vs Non-Functional Requirements ##

* Functional Requirements
	- Provides multi-threaded order traffic engine.
		
* Non-Functional Requirements
	- Provides the ability for the simulation of a financial exchange.
	- Provides delegation of an exchange feed.

## Frequently Asked Questions (FAQ) ##
* What is the Loyola-Virtual-Exchange?	
   - _Loyola-Virtual-Exchange is an open source exchange simulator that uses the FIX Protocol._

* Which software license is Loyola-Virtual-Exchange licensed under?	
   - _Loyola-Virtual-Exchange is licensed under the GNU General Public License v3.0. Refer to the license document for more information._

* Which platforms does Loyola-Virtual-Exchange support?	
   - _Loyola-Virtual-Exchange is actively tested and supported on various 64 bit versions of Windows (7 and up) and macOS._

* Who made Loyola-Virtual-Exchange? 	
   - _Group6 from COMP312/412 course. The list of contributors can be found on GitHub._

* How do I report a bug?	
   - _On the project's Github page under the issues tab please submit a new issue ticket with a descriptive but brief subject line. In the main body please provide adequate information for contributors to solve the issue._

* Who do I talk to? 
   - _Repo owner or admin_
   - _Other community members or team contact_


## IRC channel ##

* ##MES

## Development ##
* [Getting Started](https://www.youtube.com/watch?v=5rIdDNUL5iU)
* [Contribution.md](https://github.com/mgoldsmith1/Loyola-Virtual-Exchange/blob/master/osc-project-6/Contributions.md)
* [Writing Tests](https://github.com/mgoldsmith1/Loyola-Virtual-Exchange/tree/master/osc-project-6/Exchange/src/osdi/test)
* Code review
* Other guidelines

## GPL v3.0 License ##

[GNU General Public License v3.0](https://www.gnu.org/licenses/gpl-3.0.en.html)
