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

#### Profile:
~~~
Http GET to: http://<IP>:9000/api/v1/iam/profile/{id}

Path variable {id} is returned on Login.

Send header Authorization with token returned on Login.
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
Access Swagger in [http://localhost:9000](http://localhost:9000) or on Cloud [http://18.191.196.18:9000](http://18.191.196.18:9000)

## Sonar Quality Analysis
![Alt text](sonar_analysis.png?raw=true "Sonar")

## License
Apache License 2.0.
