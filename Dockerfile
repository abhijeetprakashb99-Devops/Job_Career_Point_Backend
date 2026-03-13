# Multi-stage Dockerfile for building and running the Spring Boot application

# Build stage: uses Maven with JDK to compile and package the app
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace

# copy pom and mvnw if present to leverage caching for dependencies
COPY pom.xml mvnw ./
COPY .mvn .mvn

# download dependencies
RUN mvn -B dependency:go-offline

# copy source and build
COPY src ./src
RUN mvn -B -DskipTests clean package

# Run stage: lightweight JRE image
FROM eclipse-temurin:17-jre
WORKDIR /app

# copy the packaged jar (assumes single jar in target)
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
