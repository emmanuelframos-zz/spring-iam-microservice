# IAM Microservice

## Description
A Rest Microservice using Spring Boot stack for Identity and Authorization Management.

## Running Locally
After Gradle installed, in project root directory run: 
~~~
./gradlew clean build 

and after

./gradlew bootRun
~~~

## Services

#### Signup:
~~~
Http POST to: http://<IP>:9000/api/v1/iam/signup
~~~

Body:
~~~
{
   "name":"John Doe",
   "email":"john.doe@acme.com",
   "password":"hunterxhunter",
   "phones":[
      {
         "number":"38884512",
         "ddd":"19"
      }
   ]
}
~~~

Request:
~~~
curl -H "Content-Type: application/json" -X POST -d '{ "name":"John Doe", "email":"john.doe@acme.com", "password":"hunterxhunter", "phones":[ { "number":"38884512", "ddd":"19" } ] }' http://localhost:9000/api/v1/iam/signup -v
~~~

#### Login:
~~~
Http POST to: http://<IP>:9000/api/v1/iam/login
~~~

Body:
~~~
{  
   "email":"john.doe@acme.com",
   "password":"hunterxhunter",  
}
~~~

Request:
~~~
curl -H "Content-Type: application/json" -X POST -d '{"email":"john.doe@acme.com", "password":"hunterxhunter"}' http://localhost:9000/api/v1/iam/login -v
~~~

#### Profile:
~~~
Http GET to: http://<IP>:9000/api/v1/iam/profile/{id}

Path variable {id} is returned on Login.

Send header Authorization with token returned on Login.
~~~

Request:
~~~
curl -H "Authorization: 6ed75caf-f163-4b7b-8a3f-4c8d0eb4cbae" http://localhost:9000/api/v1/iam/profile/2af5d47c-f57b-438f-bca3-1d55d4bce0ba -v
~~~

## Usage Locally
Use Postman, importing the project found in project root directory(IAM Microservice.postman_collection.json).

## Usage on Cloud 
Use Postman, importing the project found in project root directory(IAM Microservice.postman_cloud_collection.json).

## Dependencies and Supported Versions
 - Java >= 8
 - Gradle >= 4.0
 - Docker >= 17.09.0-ce

## API Swagger Documentation
Access Swagger in [http://localhost:9000](http://localhost:9000) or on Cloud [http://ec2-18-191-196-18.us-east-2.compute.amazonaws.com:9000](http://ec2-18-191-196-18.us-east-2.compute.amazonaws.com:9000)

## Sonar Quality Analysis
![Alt text](sonar_analysis.png?raw=true "Sonar")

## License
Apache License 2.0.
