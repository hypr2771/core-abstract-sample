# AS <NAME> to name this stage as maven
FROM maven:3.8.1 AS maven
LABEL MAINTAINER="nojea@paradigmadigital.com"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR
RUN mvn package 

# For Java 14,
FROM adoptopenjdk/openjdk14:alpine-jre

ARG JAR_FILE=store-api-controller.jar

WORKDIR /opt/app

# Copy the multiple-core-implem-sample.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/controller/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","store-api-controller.jar"]
