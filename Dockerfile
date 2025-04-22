# Use the official OpenJDK 17 image as the base image
FROM openjdk:17

# Set the working directory in the container
WORKDIR /app

# Copy the entire application code into the container
COPY . .

# Ensure the Maven wrapper script is executable and build the project
RUN chmod +x mvnw && ./mvnw clean install -DskipTests

# Expose the port the app will run on
EXPOSE 8080

# Specify the command to run the Spring Boot application using the JAR file
CMD ["java", "-jar", "target/SpringBootAssignment-0.0.1-SNAPSHOT.jar"]
