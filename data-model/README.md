# Data Model - Micro Service

## Requirements

- JDK 1.8
- Apache Maven 3.x
- MariaDB 10.x

## Building

The first time just use:

`$ mvn -N io.takari:maven:wrapper`

For update the structure of the database use:

`$ ./mvnw clean resources:resources liquibase:update`

For drop all objects and reconstruct the database use the parameter **liquibase.dropFirst**, for example:

`$ ./mvnw clean resources:resources liquibase:update -Dliquibase.dropFirst=true`

If you have [Docker](https://docker.io/) you can use:

`$ docker run -d --name mariadb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=XXXXX mariadb:10.0.26`

Change _XXXXX_ with your root password.

And create the database with:

`CREATE USER 'microservice'@'%' IDENTIFIED BY 'microservice123';`

`CREATE DATABASE microservice DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_bin;`

`GRANT ALL PRIVILEGES ON microservice.* to 'microservice'@'%';`

