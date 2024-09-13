# Volkswagen Cleaning Robots

## Introduction

This project is developed using Hexagonal Architecture. The main idea is to have a clean architecture that allows us to
change the implementation of the domain without affecting the rest of the application.

The main input is a file with the commands to be executed by the robot. The output is the final position of the robot
and it's displayed in the console.

This input path is specified in the main class. The file must be in the resources folder.

The application has been developed using Java 17 and Maven. The tests are developed using JUnit 5. These dependencies
are included in the `pom.xml` file.

I tried to use Kotlin instead of Java, but I don't have enough experience with Hexagonal Architecture, so I decided to
use Java... I think that's challenging enough :)

## How to run the application

The application can be run using the main class: `com.volkswagen.robot.Main` or using the
command `mvn exec:java -Dexec.mainClass="com.volkswagen.Main"`.

## How to run the tests

The tests can be run using the command `mvn test`.

There are a 92% of coverage. The classes that are not covered are the Main and some catchs that are hard to reproduce.



