# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy only essential files first to leverage Docker layer caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src




# Stage 2: Run the application using a lightweight JDK image
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port
EXPOSE 8080

ENV SPRING_DATASOURCE_URL=""
ENV SPRING_DATASOURCE_USERNAME=""
ENV SPRING_DATASOURCE_PASSWORD=""

# Run the application
CMD ["java", "-jar", "app.jar"]

