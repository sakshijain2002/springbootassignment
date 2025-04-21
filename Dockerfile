# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy the jar from build stage
COPY --from=build /app/target/SpringBootAssignment-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables (optional if you're passing via docker run)
ENV SPRING_DATASOURCE_URL=""
ENV SPRING_DATASOURCE_USERNAME=""
ENV SPRING_DATASOURCE_PASSWORD=""
ENV APP_SECRET=""

# Run the JAR
CMD ["java", "-jar", "app.jar"]
