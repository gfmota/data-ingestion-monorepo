# Use an official Gradle image to build the app
FROM gradle:7.6.0-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the project files into the Docker image
COPY . .

# Build the application
RUN gradle build --no-daemon

# Use a lightweight Java image to run the app
FROM eclipse-temurin:17-jre

# Set the working directory
WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
