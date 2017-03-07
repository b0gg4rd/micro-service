# Data Model - Micro Service

## Requirements

- JDK 1.8
- Apache Maven 3.x
- MariaDB 10.x

## Contribution guide

## Building

The first time just use:

`$ mvn -N io.takari:maven:wrapper`

For update the structure of the database use:

`$ ./mvnw clean resources:resources liquibase:update`

For drop all objects and reconstruct the database use the parameter **liquibase.dropFirst**, for example:

`$ ./mvnw clean resources:resources liquibase:update -Dliquibase.dropFirst=true`

If you have [Docker](https://docker.io/) use:

`S docker run -d -p 3306:3306 --name mariadb-local mariadb:10.0.26`

And to create the database with:

`CREATE USER 'microservice'@'%' IDENTIFIED BY 'microservice123';`
`CREATE DATABASE microservice DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;`
`GRANT ALL PRIVILEGES ON microservice.* to 'microservice'@'%';`

