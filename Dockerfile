FROM gradle:8.14.3-jdk17 AS build
WORKDIR /workspace

COPY build.gradle settings.gradle ./
RUN gradle --no-daemon dependencies > /dev/null

COPY src ./src
RUN gradle --no-daemon bootJar

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/build/libs/digital-bank-api-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
