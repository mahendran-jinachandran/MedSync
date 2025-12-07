# 1. Use Java 22 runtime (same as other services)
FROM eclipse-temurin:22-jdk

# 2. Workdir inside the container
WORKDIR /app

# 3. Copy the built Spring Boot JAR into the container
COPY target/doctor-service-*.jar app.jar

# 4. Document internal port (doctor-service listens on 8083)
EXPOSE 8083

# 5. Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
