FROM eclipse-temurin:17-jre
WORKDIR /app

COPY target/digital-bank-api-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
