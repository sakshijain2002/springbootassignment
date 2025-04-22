FROM openjdk:17
WORKDIR /app
COPY . .

# Ensure mvnw is executable and run the build
RUN chmod +x mvnw && ./mvnw clean install -DskipTests

# Expose the application port
EXPOSE 8080

# Run the built JAR (assuming the JAR file will be in 'target' after build)
CMD ["java", "-jar", "target/SpringBootAssignment-0.0.1-SNAPSHOT.jar"]









