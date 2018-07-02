# IAM Microservice

## Description
A Rest Microservice using Spring Boot stack for Identity and Authorization Management.

## Installing dependencies
- [Install](http://www.oracle.com/technetwork/java/javase/downloads/index.html) Java
- [Install](https://docs.docker.com/engine/installation/) Docker
- [Install](https://docs.docker.com/compose/install/) Docker Compose

## Running application with Docker local
- bin/run.sh

## Running application
1. Access application root directory
2. To run inside a **docker container** execute: **run-in-docker.sh** file

## Usage

We have to register an user to access services:
~~~~
curl -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password": "p4ssw0rd"}' http://localhost:8080/api/v1/users/register -v
~~~~

After user registered, we must have the access granted using auth method, a JWT token will be returned if user credentials is ok:
~~~~
curl -i -H "Content-Type: application/json" -X POST -d '{"username": "admin", "password": "p4ssw0rd"}' http://localhost:8080/api/v1/users/auth -v
~~~~

With token, we can access protected endpoints, as customer listing service:
~~~~
curl -H "Content-Type: application/json" -H "Authorization: Bearer [TOKEN RETURNED]" http://localhost:8080/api/v1/customers -v
~~~~

And customer's prize listing service:
~~~~
curl -H "Content-Type: application/json" -H "Authorization: Bearer [TOKEN RETURNED]" http://localhost:8080/api/v1/customers/prize -v
~~~~

## Supported Versions
We recommend that you use:
 - Java >= 8
 - Gradle >= 3.0
 - Docker >= 17.09.0-ce
 - Docker Compose >= 1.17.0

## API Documentation
Access Swagger in [http://localhost:8080](http://localhost:8080)

## License
It is free software, and may be redistributed.
