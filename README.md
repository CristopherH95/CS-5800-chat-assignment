# CS-5800: Chat Assignment

This repository contains an implementation of a simulated chat app for CS-5800 (Advanced Software Engineering).
Essentially, the mediator design pattern is used to manage communication between user instances, and the memento
design pattern is used to support an undo function for the most recently sent message of a user.
The `driver` package implements a basic program demonstrating the instantiation of the different classes implemented here. 
An example of its output can be found in the `output.png` file.

## Build & Run

To build with Maven, simply navigate to the project root in the command line and run:

```shell
mvn package
```

Alternatively, IDEs such as IntelliJ should be able to build the source files using their standard build utilities.

Once built, the project can be run using the `driver` package:

```shell
java -cp ./target/chat-assignment-1.0-SNAPSHOT.jar driver.Main
```