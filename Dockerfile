# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copy only essential files first to leverage Docker layer caching


ENV SPRING_DATASOURCE_URL=""
ENV SPRING_DATASOURCE_USERNAME=""
ENV SPRING_DATASOURCE_PASSWORD=""

# Expose port 8080 for the application
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]

