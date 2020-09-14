FROM arm32v7/openjdk:11.0.1-slim

COPY /target/backend-1.0-SNAPSHOT.jar /usr/local/lib/tto-backend.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/local/lib/tto-backend.jar"]