FROM openjdk:14-jdk-alpine
COPY "./target/controller-1.0.jar" "app.jar"
ENTRYPOINT ["java","-jar","/app.jar"]
VOLUME /tmp
EXPOSE 8080