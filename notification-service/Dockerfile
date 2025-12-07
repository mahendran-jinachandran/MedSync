# 1. Use the same Java runtime as other services
FROM eclipse-temurin:22-jdk

# 2. Workdir inside the container
WORKDIR /app

# 3. Copy the built Spring Boot JAR into the container
COPY target/notification-service-*.jar app.jar

# 4. Document internal port (for debug endpoints etc.)
EXPOSE 8085

# 5. Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
