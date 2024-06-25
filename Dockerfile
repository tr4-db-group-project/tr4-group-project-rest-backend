FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
ARG SKIP_TESTS=true
RUN if [ "$SKIP_TESTS" = "true" ]; then mvn clean package -DskipTests; else mvn clean package; fi

FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build app/target/restapi-0.0.1.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
