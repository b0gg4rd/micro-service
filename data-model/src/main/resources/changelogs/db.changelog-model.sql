--liquibase formatted sql
--DDL script for micro service

--changeset b0gg4rd:table_person
--Create table person
CREATE TABLE person (
  id       INT          NOT NULL,
  name     VARCHAR(100) NULL,
  birthday DATE         NULL,
  age      INT          NULL,
  PRIMARY KEY (id)
);

