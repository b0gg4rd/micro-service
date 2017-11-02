#!/bin/sh
for i in {1..30}
do
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
  curl -X GET "http://localhost:8080/api/v1/persons/?pageSize=10&pageNumber=10" -H "accept: application/json" &
done
