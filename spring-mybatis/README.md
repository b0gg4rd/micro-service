# Spring - MyBatis

A _microservice_ for the Person domain with **Spring** and **MyBatis**.

## Requirements

- JDK 1.8
- Apache Maven 3.x

## Building

The first time just use:

`$ mvn -N io.takari:maven:wrapper`

For execute with _local_ profile use:

`$ ./mvnw clean package exec:exec -P local`

For integration test with _local_ profile use:

`$ ./mvnw clean verify -P local`

