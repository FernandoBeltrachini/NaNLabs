FROM openjdk:17-oracle
MAINTAINER fernando.beltrachini@gmail.com
COPY target/spacex-1.0.0.jar spacex-api.jar
ENTRYPOINT ["java","-jar","/spacex-api.jar"]