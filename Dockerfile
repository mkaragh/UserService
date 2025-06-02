FROM openjdk:17-jdk-alpine
MAINTAINER aravinda.com
COPY target/UserService-0.0.1-SNAPSHOT.jar UserService-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/UserService-0.0.1-SNAPSHOT.jar"]