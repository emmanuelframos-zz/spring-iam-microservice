# IAM Microservice

## Description
A Rest Microservice using Spring Boot stack for Identity and Authorization Management.

## Accessing on Cloud

## Running Locally

## Usage

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
 - Gradle >= 4.0
 - Docker >= 17.09.0-ce

## API Swagger Documentation
Access Swagger in [http://localhost:9000](http://localhost:9000)

## License
It is free software, and may be redistributed.