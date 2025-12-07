# 1. Use official Java runtime (same as auth-service)
FROM eclipse-temurin:22-jdk

# 2. Workdir inside the container
WORKDIR /app

# 3. Copy the built Spring Boot fat JAR into the container
COPY target/patient-service-*.jar app.jar

# 4. Document the internal port (patient-service uses 8082)
EXPOSE 8082

# 5. Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
