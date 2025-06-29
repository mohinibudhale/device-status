FROM eclipse-temurin:21-jdk as base
COPY target/device-status-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]