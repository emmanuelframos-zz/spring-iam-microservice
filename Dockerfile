FROM openjdk:8-jre-alpine

ENV HOME=/home/iam-microservice

WORKDIR $HOME

ADD build/libs/iam-microservice.jar iam-microservice.jar

CMD ["java", "-jar", "iam-microservice.jar"]
