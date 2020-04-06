# Clarity Code Challenge

This is my solution proposal of the code challenge for the Data Engineer position at Clarity.

## Description

Following the document requirements, I develop two applications in Java:

* *listhosts* is my approach to solve the first requirement: `Parse the data with a time_init, time_end`
* *unlimitedinputparser* is my approach to handle the second requirement: `Unlimited Input Parser`

To be able to test the second requirement, I created a tool that generates log lines following the structure of the logfile that I've got with the challenge document.

## Building the project

### Prerequisites

* Java 11 or higher is installed

### Project structure

The project is a gradle multi-project that contains three subprojects:

* listhosts folder contains the source code for the tool that given the name of a file (with the format described above), an init_datetime, an end_datetime, and a Hostname, returns a list of hostnames connected to the given host during the given period
* unlimitedinputparser folder contains the source code for the tool that can both parse previously written log files and terminate or collect input from a new log file while it's being written and run indefinitely.
* loggenerator contains the tool that can generate log lines indefinitely

### How to build

The binaries can be generated using the gradle wrapper:

    ./gradlew clean build

This will generate a jar file inside each subproject, in the following path

    ./build/libs/

## Using the tools

### Parse the data with a time_init, time_end

After the tool finishes parsing the log, it'll print a list of a list of hostnames connected to the given host during the given period

    java -jar ./listhosts/build/libs/listhosts-1.0.jar <FILE> <init_date> <end_date> <hostname>

#### Parameters

* FILE: The file to be processed.
* init_date: The datetime that will be used as start datetime of the given period.
* end_date: The datetime that will be used as the end datetime of the given period.
* hostname: The hostname that is the target host of a connection from the hosts in the result list.

Example:

    java -jar listhosts/build/libs/listhosts-1.0.jar test/input-file-10000_4.txt "2019-08-12 22:00:00" "2019-08-13 22:00:00" Rehgan 

### Unlimited Input Parser

To parse a previously written log file and print the output once is done:

    java -jar unlimitedinputparser/build/libs/unlimitedinputparser-1.0.jar <hostname> <FILE>

To collect input from a new log file while it's being written and run indefinitely:

    tail -f <FILE> | java -jar unlimitedinputparser/build/libs/unlimitedinputparser-1.0.jar <hostname>

In this case, the tool will display the output every hour.

#### Parameters

* FILE: The file to be processed.
* hostname: The hostname that is the target host of a connection from the hosts in the result list.

Examples:
* For a previously written log file:
    
        java -jar unlimitedinputparser/build/libs/unlimitedinputparser-1.0.jar Rehgan test/input-file-10000_4.txt
 
* For a log file being written:

        java -jar loggenerator/build/libs/loggenerator-1.0.jar | java -jar unlimitedinputparser/build/libs/unlimitedinputparser-1.0.jar Kratos
     
     or   
     
        tail -f test/input-file-10000_4.txt | java -jar unlimitedinputparser/build/libs/unlimitedinputparser-1.0.jar Rehgan
     
### Log generator

To generate log lines in a file indefinitely:

    java -jar ./loggenerator/build/libs/loggenerator-1.0.jar > <FILE>
    
This tool can receive a number of milliseconds as parameter to be used as a delay. The default is 100ms if no parameter is given.
