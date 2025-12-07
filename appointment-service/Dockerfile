# 1. Use Java 22 runtime (same as other services)
FROM eclipse-temurin:22-jdk

# 2. Workdir inside container
WORKDIR /app

# 3. Copy the built Spring Boot JAR
COPY target/appointment-service-*.jar app.jar

# 4. Document the internal port (appointment-service listens on 8084)
EXPOSE 8084

# 5. Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
