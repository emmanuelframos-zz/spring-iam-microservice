#!/bin/sh

./gradlew clean build test

docker stop $(docker ps -qa --filter="name=iam-microservice") || true

docker rm -f $(docker ps -qa --filter="name=iam-microservice") || true

docker build -t iam-microservice:latest .

docker run -d -p 9000:9000 --restart=always --name iam-microservice -t iam-microservice:latest